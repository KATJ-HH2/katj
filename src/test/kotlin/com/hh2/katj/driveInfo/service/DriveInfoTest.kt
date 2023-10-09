package com.hh2.katj.driveInfo.service

import com.hh2.katj.driveInfo.model.RequestDriveInfo
import com.hh2.katj.user.model.User
import com.hh2.katj.taxiDriver.model.TaxiDriver
import com.hh2.katj.taxiDriver.repository.historyRepository
//import com.hh2.katj.comm.repository.tripRepository
import jakarta.transaction.Transactional
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import javax.management.loading.ClassLoaderRepository

@Transactional
@SpringBootTest
class DriveInfoTest @Autowired constructor(
        private val historyRepository: HistoryRepository,
//        private val tripRepository: TripRepository,
        private val driveInfoService: DriveInfoService,
        private val departure: String? = null,
        private val destination: String? = null,
        private val fare: String? = null
){
    @Test
    fun `매칭된 사용자에 대한 경로와 예상 요금 조회`(){
        // given
        val taxiDriver: TaxiDriver = TaxiDriver(
                id = "1",
                driver_license_id = "서울 13고 1234",
                security_id = ""
        )

        val user: User = User(
                id = "1",
                phoneNumber = "010-0000-0000"
        )

        val driveInfo: DriveInfo = DriveInfo(
                departure: String? = null,
                destination: String? = null,
                fare: String? = null
        )

        // when
        val is_matched: Boolean = historyRepository.judge(taxiDriver, user)
//        val trip_id: Long = tripRepository.findId(taxiDriver, user)
        val requestDriveInfo: RequestDriveInfo = RequestDriveInfo(
                taxiDriver.id,
                user.id
        )
//        val requestDriveInfo: RequestDriveInfo = RequestDriveInfo(
//                trip_id
//        )
        // then
        val result: Boolean = driveInfoService.getInfo(is_matched, requestDriveInfo)
//        val result: Boolean = driveInfoService.getInfo(trip_id, requestDriveInfo)
        org.assertj.core.api.Assertions.assertThat(result).isTrue()

    }
}