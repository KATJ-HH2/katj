package com.hh2.katj.history.service

import com.hh2.katj.history.model.entity.SearchRouteHistory
import com.hh2.katj.trip.model.DepartureRoadAddress
import com.hh2.katj.trip.model.DestinationRoadAddress
import com.hh2.katj.user.model.entity.User
import com.hh2.katj.user.model.entity.UserStatus
import com.hh2.katj.user.repository.UserRepository
import com.hh2.katj.util.annotation.KATJTestContainerE2E
import com.hh2.katj.util.model.BaseTestEntity
import com.hh2.katj.util.model.Gender
import com.hh2.katj.util.model.RoadAddress
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

@KATJTestContainerE2E
class RouteHistoryServiceTest(
    private val routeHistoryService: RouteHistoryService,
    private val userRepository: UserRepository,
) : BaseTestEntity() {

    @Test
    fun `출도착 정보로 택시 요금 조회`() {
        // given
        val roadAddress = RoadAddress(
            addressName = "서울 관악구 법원단지5가길 76",
            region1depthName = "서울",
            region2depthName = "관악구",
            region3depthName = "신림동",
            roadName = "법원단지5가길",
            undergroundYn = "N",
            mainBuildingNo = "76",
            subBuildingNo = "",
            buildingName = "대명아트빌",
            zoneNo = "08852",
            longitude = "126.923157313768",
            latitude = "37.4764786774284",
        )

        val departureRoadAddress = DepartureRoadAddress(
            departureAddressName = "서울 관악구 법원단지5가길 76",
            departureRegion1depthName = "서울",
            departureRegion2depthName = "관악구",
            departureRegion3depthName = "신림동",
            departureRoadName = "법원단지5가길",
            departureUndergroundYn = "N",
            departureMainBuildingNo = "76",
            departureSubBuildingNo = "",
            departureBuildingName = "대명아트빌",
            departureZoneNo = "08852",
            departureLongitude = "126.923157313768",
            departureLatitude = "37.4764786774284",
        )

        val destinationRoadAddress = DestinationRoadAddress(
            destinationAddressName = "서울 중구 세종대로 110",
            destinationRegion1depthName = "서울",
            destinationRegion2depthName = "중구",
            destinationRegion3depthName = "태평로1가",
            destinationRoadName = "세종대로",
            destinationUndergroundYn = "N",
            destinationMainBuildingNo = "76",
            destinationSubBuildingNo = "",
            destinationBuildingName = "서울특별시청",
            destinationZoneNo = "04524",
            destinationLongitude = "126.977829174031",
            destinationLatitude = "37.5663174209601",
        )

        val user = User(
            name = "탁지성",
            phoneNumber = "010-3253-5576",
            email = "user@gmail.com",
            gender = Gender.MALE,
            status = UserStatus.ACTIVE,
            roadAddress = roadAddress,
        )

        val routeHistory = SearchRouteHistory(
            user = user,
            departureRoadAddress = departureRoadAddress,
            destinationRoadAddress = destinationRoadAddress,
        )

        // when
        val saveUser = userRepository.save(user)
        val summary = routeHistoryService.addRouteHistory(saveUser.id, routeHistory)

        // then
        assertThat(summary.fare.taxiFare).isGreaterThan(0)
    }
    
    @Test
    fun `API 호출 실패시 예외 반환`() {
        // given

        // when

        // then

    }
}