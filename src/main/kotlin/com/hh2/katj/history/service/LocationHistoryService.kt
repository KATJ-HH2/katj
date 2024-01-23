package com.hh2.katj.history.service

import com.hh2.katj.history.component.KakaoApiManager
import com.hh2.katj.history.component.LocationHistoryManager
import com.hh2.katj.history.model.dto.ResponseLocationHistory
import com.hh2.katj.user.model.entity.User
import com.hh2.katj.util.exception.ExceptionMessage
import com.hh2.katj.util.exception.failWithMessage
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import io.github.resilience4j.retry.annotation.Retry
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit
import kotlin.random.Random

//val logger = KotlinLogging.logger {}

@Service
class LocationHistoryService(
    private val locationHistoryManager: LocationHistoryManager,
    private val kakaoApiManager: KakaoApiManager,
//    private val circuitBreaker: io.github.resilience4j.circuitbreaker.CircuitBreaker
    @Autowired private val myCircuitBreaker: io.github.resilience4j.circuitbreaker.CircuitBreaker
) {
    @CircuitBreaker(name="saveLocationHistory")
    fun saveLocationHistory(user: User,
                            keyword: String,
                            faultPercentage: Int): ResponseLocationHistory {
        val response = kakaoApiManager.requestAddressSearch(keyword)
        val random = Random.nextInt(0, 100)
        if (faultPercentage > random) {
            myCircuitBreaker.onError(0, TimeUnit.SECONDS, RuntimeException())
            // OPEN 상태에서만 fallback 메서드를 직접 호출
            if (myCircuitBreaker.state == io.github.resilience4j.circuitbreaker.CircuitBreaker.State.OPEN) {
                return fallbackSaveLocationHistory(user, keyword, faultPercentage, RuntimeException("CircuitBreaker is OPEN"))
            }
        }
        checkNotNull(response) { "api 호출 오류" }
        check(response.documents.isNotEmpty()) {
            failWithMessage(ExceptionMessage.NO_SEARCH_RESULT.name)
        }
        myCircuitBreaker.onSuccess(0, TimeUnit.SECONDS)
        val roadAddress = response.documents[0].roadAddress

        return locationHistoryManager.addLocationHistory(user, keyword, roadAddress).toResponseDto()
    }

    fun fallbackSaveLocationHistory(user: User,
                                    keyword: String,
                                    faultPercentage: Int,
                                    t: Throwable): ResponseLocationHistory {

        println("fallback function is acting! -> " + t.message)
        val response = kakaoApiManager.requestAddressSearch(keyword)
        checkNotNull(response) { "api 호출 오류" }
        check(response.documents.isNotEmpty()) {
            failWithMessage(ExceptionMessage.NO_SEARCH_RESULT.name)
        }

        val roadAddress = response.documents[0].roadAddress
        return locationHistoryManager.addLocationHistory(user, keyword, roadAddress).toResponseDto()
    }
}