package com.hh2.katj.history.service

import com.hh2.katj.history.repository.LocationHistoryRepository
import com.hh2.katj.user.model.entity.Gender
import com.hh2.katj.user.model.entity.User
import com.hh2.katj.user.model.entity.UserStatus
import com.hh2.katj.user.repository.UserRepository
import com.hh2.katj.util.annotation.KATJTestContainerE2E
import com.hh2.katj.util.exception.ExceptionMessage
import com.hh2.katj.util.model.BaseTestEnitity
import com.hh2.katj.util.model.RoadAddress
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

@KATJTestContainerE2E
class LocationHistoryServiceTest(
    private val locationHistoryRepository: LocationHistoryRepository,
    private val locationHistoryService: LocationHistoryService,
    private val userRepository: UserRepository,
): BaseTestEnitity() {

    @AfterEach
    fun tearUp() {
        userRepository.deleteAllInBatch()
        locationHistoryRepository.deleteAllInBatch()
    }

    @Test
    fun `검색어를 통해 위치정보를 찾고 이력을 저장한다`() {
        // given
        val user = initUser()

        // when
        val saveUser = userRepository.save(user)
        val response = locationHistoryService.saveLocationHistory(saveUser, keyword = "법원단지5가길 76")

        // then
        assertThat(response.roadAddress.addressName).isEqualTo("서울 관악구 법원단지5가길 76")
    }

    @Test
    fun `검색어로 주소 검색후 결과가 없으면 예외 반환`() {
        // given
        val user = initUser()

        // when
        val saveUser = userRepository.save(user)

        // then
        assertThrows<IllegalArgumentException> {
            locationHistoryService.saveLocationHistory(saveUser, keyword = "법원단지5가길 76555")
        }.apply {
            assertThat(message).isEqualTo(ExceptionMessage.NO_SEARCH_LOCATION_RESULT.name)
        }
    }

    private fun initUser(): User {
        val roadAddress = RoadAddress(
            addressName = "서울 관악구 법원단지5가길 76",
            buildingName = "대명아트빌",
            mainBuildingNo = "76",
            region1depthName = "서울",
            region2depthName = "관악구",
            region3depthName = "신림동",
            roadName = "법원단지5가길",
            subBuildingNo = "",
            undergroundYn = "N",
            longitude = "126.923157313768",
            latitude = "37.4764786774284",
            zoneNo = "08852",
        )

        return User(
            name = "탁지성",
            phoneNumber = "01032535576",
            email = "email@naver.com",
            gender = Gender.MALE,
            status = UserStatus.ACTIVE,
            roadAddress = roadAddress,
        )
    }

}