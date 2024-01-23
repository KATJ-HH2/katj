package com.hh2.katj.history.service

import com.hh2.katj.history.repository.LocationHistoryRepository
import com.hh2.katj.user.model.entity.User
import com.hh2.katj.user.model.entity.UserStatus
import com.hh2.katj.user.repository.UserRepository
import com.hh2.katj.util.model.BaseTestEntity
import com.hh2.katj.util.model.Gender
import com.hh2.katj.util.model.RoadAddress
import io.github.resilience4j.circuitbreaker.CircuitBreaker
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import java.lang.RuntimeException
import java.util.concurrent.TimeUnit


@TestConfiguration
class TestConfig {
    @Bean
    fun testCircuitBreakerConfig(): CircuitBreakerConfig {
        return CircuitBreakerConfig.custom()
            .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.COUNT_BASED) // COUNT 또는 TIME
            .slidingWindowSize(10) // CLOSED 상태에서의 실패율 기록 단위
            .failureRateThreshold(30.0F) // 실패율 임계치
            .minimumNumberOfCalls(3) // 실패율 계산을 위한 최소 call 횟수
            .automaticTransitionFromOpenToHalfOpenEnabled(true) // OPEN -> HALF-OPEN 자동 전환
            .waitDurationInOpenState(java.time.Duration.ofSeconds(1)) // OPEN -> HALF-OPEN 전환 시간
            .permittedNumberOfCallsInHalfOpenState(3) // HALF-OPEN 상태에서 최대로 허용되는 call 횟수
            .build()
    }

    @Bean
    fun testCircuitBreakerRegistry(circuitBreakerConfig: CircuitBreakerConfig): CircuitBreakerRegistry {
        return CircuitBreakerRegistry.of(circuitBreakerConfig)
    }

    @Primary
    @Bean
    fun testCircuitBreaker(circuitBreakerRegistry: CircuitBreakerRegistry): CircuitBreaker {
        return CircuitBreaker.of("testCircuitBreaker", testCircuitBreakerConfig())
    }
}

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = [TestConfig::class])
class CircuitBreakerTest @Autowired constructor(
    private val locationHistoryRepository: LocationHistoryRepository,
    private val locationHistoryService: LocationHistoryService,
    private val userRepository: UserRepository,
    @Autowired(required = false) private val testCircuitBreaker: CircuitBreaker
): BaseTestEntity() {
    @BeforeEach
    fun setUp() {
        testCircuitBreaker.reset()
    }

    @AfterEach
    fun tearUp() {
        userRepository.deleteAllInBatch()
        locationHistoryRepository.deleteAllInBatch()
    }

    // CLOSED
    @Test
    fun `한 번의 호출로 성공`() {
        // given: user, keyword
        val user = initUser()
        val keyword = "서울시 성동구 고산자로2길 65"

        // when: 성공
        val faultPercentage = 0
        val saveUser = userRepository.save(user)
        locationHistoryService.saveLocationHistory(saveUser, keyword, faultPercentage)

        // then: CLOSED
        Assertions.assertThat(testCircuitBreaker.state).isEqualTo(CircuitBreaker.State.CLOSED)
    }

    @Test
    fun `임계점 초과 직전 성공`() {
        // given: user, keyword
        val user = initUser()
        val keyword = "서울시 성동구 고산자로2길 65"

        // when: 실패율 임계치 30% 미달 (window size:10, call 3회부터 실패율 계산)
        val saveUser = userRepository.save(user)
        locationHistoryService.saveLocationHistory(saveUser, keyword, 100)

        // then: CLOSED
        Assertions.assertThat(testCircuitBreaker.state).isEqualTo(CircuitBreaker.State.CLOSED)
    }

    // OPEN
    @Test
    fun `임계점 달성으로 인한 회로 OPEN`() {
        // given: user, keyword
        val user = initUser()
        val keyword = "서울시 성동구 고산자로2길 65"

        // when: 실패율 임계치 30% 달성 (window size:10, call 3회부터 실패율 계산)
        val saveUser = userRepository.save(user)
        for (i in 1..3) {
            locationHistoryService.saveLocationHistory(saveUser, keyword, 100)
        }

        // then: OPEN & retry 수행
        Assertions.assertThat(testCircuitBreaker.state).isEqualTo(CircuitBreaker.State.OPEN)
    }

    // HALF-OPEN
    @Test
    fun `임계점을 달성하여 회로 OPEN 후 1초가 지나면 HALF-OPEN`() {
        // given: user, keyword
        val user = initUser()
        val keyword = "서울시 성동구 고산자로2길 65"

        // when: 실패율 임계치 30% 달성 후 1초 경과 (wait duration in open state: 1)
        val saveUser = userRepository.save(user)
        for (i in 1..3) {
            locationHistoryService.saveLocationHistory(saveUser, keyword, 100)
        }
        Thread.sleep(1000)

        // then
        Assertions.assertThat(testCircuitBreaker.state).isEqualTo(CircuitBreaker.State.HALF_OPEN)
    }

    @Test
    fun `HALF-OPEN 상태에서 3번의 요청 실패 시 OPEN`() {
        // given: user, keyword
        val user = initUser()
        val keyword = "서울시 성동구 고산자로2길 65"

        // when: HALF-OPEN 상태에서 3번 실패 (실패율 임계치와 동일 적용)
        val saveUser = userRepository.save(user)
        for (i in 1..3) {
            locationHistoryService.saveLocationHistory(saveUser, keyword, 100)
        }
        Thread.sleep(1000)
        for (i in 1..3) {
            locationHistoryService.saveLocationHistory(saveUser, keyword, 100)
        }

        // then: OPEN
        Assertions.assertThat(testCircuitBreaker.state).isEqualTo(CircuitBreaker.State.OPEN)
    }

    @Test
    fun `HALF-OPEN 상태에서 3번의 요청 성공 시 CLOSED`() {
        // given: user, keyword
        val user = initUser()
        val keyword = "서울시 성동구 고산자로2길 65"

        // when: HALF-OPEN 상태에서 permitted number of calls in half open state 동안 모두 성공
        val saveUser = userRepository.save(user)
        for (i in 1..3) {
            locationHistoryService.saveLocationHistory(saveUser, keyword, 100)
        }
        Thread.sleep(1000)
        for (i in 1..3) {
            locationHistoryService.saveLocationHistory(saveUser, keyword, 0)
        }

        // then: CLOSED
        Assertions.assertThat(testCircuitBreaker.state).isEqualTo(CircuitBreaker.State.CLOSED)
    }

    private fun initUser(): User {
        val roadAddress = RoadAddress(
            addressName = "서울 성동구 고산자로2길 65",
            buildingName = "서울숲리버뷰자이",
            mainBuildingNo = "65",
            region1depthName = "서울",
            region2depthName = "성동구",
            region3depthName = "행당동",
            roadName = "고산자로2길",
            subBuildingNo = "",
            undergroundYn = "N",
            longitude = "127.038836506223",
            latitude = "37.556198526017",
            zoneNo = "04745",
        )

        return User(
            name = "안유진",
            phoneNumber = "01012345678",
            email = "email@naver.com",
            gender = Gender.FEMALE,
            status = UserStatus.ACTIVE,
            roadAddress = roadAddress,
        )
    }
}