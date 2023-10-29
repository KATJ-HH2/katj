package com.hh2.katj.taxidriver.controller

import com.hh2.katj.taxi.model.ChargeType
import com.hh2.katj.taxi.model.FuelType
import com.hh2.katj.taxi.model.Taxi
import com.hh2.katj.taxi.repository.TaxiRepository
import com.hh2.katj.taxidriver.model.entity.TaxiDriverStatus
import com.hh2.katj.taxidriver.fixtures.TaxiDriverFixtures.Companion.드라이버_생성
import com.hh2.katj.taxidriver.repository.TaxiDriverRepository
import com.hh2.katj.util.annotation.KATJTestContainerE2E
import com.hh2.katj.util.model.BaseTestEntity
import com.hh2.katj.util.model.Gender
import com.hh2.katj.util.model.RoadAddress
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.time.LocalDate

@KATJTestContainerE2E
class TaxiDriverAcceptanceTest(
    private val taxiDriverRepository: TaxiDriverRepository,
    private val taxiRepository: TaxiRepository,
): BaseTestEntity() {

    @BeforeEach
    fun setUp() {
        taxiDriverRepository.deleteAllInBatch()
        taxiRepository.deleteAllInBatch()
    }

    @DisplayName("드라이버 정보 저장")
    @Test
    fun creteTaxi() {
        val taxi = Taxi(
                carNo = "62거4811",
                vin = "KMHDL41BP8A000001",
                kind = ChargeType.NORMAL,
                manufactureDate = LocalDate.now(),
                fuel = FuelType.GASOLINE,
                color = "white",
                insureYN = true,
                accidentYN = true,
                )
        val address = RoadAddress(
                addressName = "address_name_three",
                region1depthName = "rg1",
                region2depthName = "rg2",
                region3depthName = "rg3",
                roadName = "road_name",
                undergroundYn = "Y",
                mainBuildingNo = "mbNo",
                subBuildingNo = "subNo",
                buildingName = "bName",
                zoneNo = "1",
                longitude = "x.123",
                latitude = "y.321",
                )
        val taxiDriver = 드라이버_생성(
                taxi = taxi,
                driverLicenseId = "11",
                issueDate = LocalDate.now(),
                securityId = "test",
                name = "안유진",
                status = TaxiDriverStatus.WAITING,
                gender = Gender.FEMALE,
                address = address,
                img = "1111"
        )
        val securityId = taxiDriver.jsonPath().getString("securityId")

        Assertions.assertThat(securityId).isEqualTo("test")
    }

}