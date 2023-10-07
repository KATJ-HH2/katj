package com.hh2.katj.trip.service

import com.hh2.katj.domain.trip.service.DriverTripService
import com.hh2.katj.trip.service.DriverTripService
import com.hh2.katj.trip.model.Trip
import com.hh2.katj.trip.component.TripReader
import com.hh2.katj.trip.repository.TripRepository
//import com.hh2.katj.trip.component.TripSpec
import com.hh2.katj.util.Gender
import com.hh2.katj.taxi.model.Taxi
import com.hh2.katj.taxi.repository.TaxiRepository
import com.hh2.katj.taxidriver.model.TaxiDriver
import com.hh2.katj.taxidriver.repository.DriverRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestConstructor

@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class DriverTripServiceTest (
    private val driverTripService: DriverTripService
){
    @Test
    fun `매칭된 사용자에 대한 경로와 예상 요금 조회`(){
        // given
        val tripId = 1

//        val taxiDriver: TaxiDriver = TaxiDriver(
//            id = "1",
//            driver_license_id = "서울 13고 1234",
//            security_id = ""
//        )
//
//        val user: User = User(
//            id = "1",
//            phoneNumber = "010-0000-0000"
//        )

//        val driveInfo: DriveInfo = DriveInfo(
//            departure: String? = null,
//            destination: String? = null,
//            fare: String? = null
//        )

        // when
        val tripInfo = driverTripService.getTripInfo(tripId)

//        val is_matched: Boolean = historyRepository.judge(taxiDriver, user)
////        val trip_id: Long = tripRepository.findId(taxiDriver, user)
//        val requestDriveInfo: RequestDriveInfo = RequestDriveInfo(
//            taxiDriver.id,
//            user.id
//        )
//        val requestDriveInfo: RequestDriveInfo = RequestDriveInfo(
//                trip_id
//        )
        // then
        val departure = tripInfo.get(index = 0)
        val destination = tripInfo.get(index = 1)
        val fare = tripInfo.get(index = 2)

        assertThat(departure).isEqualTo("왕십리")
        assertThat(destination).isEqualTo("선릉")
        assertThat(fare).isEqualTo(10000)
    }
}