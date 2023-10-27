package com.hh2.katj.taxidriver.service

import com.hh2.katj.taxi.model.ChargeType
import com.hh2.katj.taxi.model.FuelType
import com.hh2.katj.taxi.model.Taxi
import com.hh2.katj.taxi.repository.TaxiRepository
import com.hh2.katj.taxidriver.model.dto.request.AddTotalInfoRequest
import com.hh2.katj.taxidriver.model.dto.request.UpdateStatusRequest
import com.hh2.katj.taxidriver.model.entity.TaxiDriver
import com.hh2.katj.taxidriver.model.entity.TaxiDriverStatus
import com.hh2.katj.taxidriver.repository.TaxiDriverRepository
import com.hh2.katj.util.annotation.KATJTestContainerE2E
import com.hh2.katj.util.exception.ExceptionMessage
import com.hh2.katj.util.model.BaseTestEntity
import com.hh2.katj.util.model.Gender
import com.hh2.katj.util.model.RoadAddress
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.LocalDate

@KATJTestContainerE2E
class TaxiDriverServiceTest(
    private val taxiDriverRepository: TaxiDriverRepository,
    private val taxiRepository: TaxiRepository,
    private val taxiDriverService: TaxiDriverService,
): BaseTestEntity() {

    @BeforeEach
    fun setUp() {
        taxiDriverRepository.deleteAllInBatch()
        taxiRepository.deleteAllInBatch()
    }

    @Test
    fun `드라이버를 등록한다`() {
        // given: 택시 정보, 주소 정보, 드라이버 정보
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
        val taxiDriver = TaxiDriver(
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

        // when: 드라이버 정보 저장
        val requestTaxiDriver = AddTotalInfoRequest(
            taxi = taxiDriver.taxi,
            driverLicenseId = taxiDriver.driverLicenseId,
            issueDate = taxiDriver.issueDate,
            securityId = taxiDriver.securityId,
            name = taxiDriver.name,
            status = taxiDriver.status,
            gender = taxiDriver.gender,
            address = taxiDriver.address,
            img = taxiDriver.img
        )

        // then: 등록한 드라이버 정보가 requestTaxiDriver 정보와 일치하는지 확인
        val responseTaxiDriver = taxiDriverService.saveTotalInfo(requestTaxiDriver.toEntity())

        assertThat(responseTaxiDriver.taxi).isEqualTo(requestTaxiDriver.taxi)
        assertThat(responseTaxiDriver.driverLicenseId).isEqualTo(requestTaxiDriver.driverLicenseId)
        assertThat(responseTaxiDriver.securityId).isEqualTo(requestTaxiDriver.securityId)
        assertThat(responseTaxiDriver.name).isEqualTo(requestTaxiDriver.name)
        assertThat(responseTaxiDriver.gender).isEqualTo(requestTaxiDriver.gender)
    }

    @Test
    fun `driverLicenseId가 이미 존재하면 드라이버 등록에 실패한다`() {
        // given: driverLiscenseId가 일치하는 두 명의 드라이버 정보
        val taxi1 = Taxi(
            carNo = "62거4811",
            vin = "KMHDL41BP8A000001",
            kind = ChargeType.NORMAL,
            manufactureDate = LocalDate.now(),
            fuel = FuelType.GASOLINE,
            color = "white",
            insureYN = true,
            accidentYN = true,
        )
        val address1 = RoadAddress(
            addressName = "address_name_one",
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
        val taxiDriver1 = TaxiDriver(
            taxi = taxi1,
            driverLicenseId = "11",
            issueDate = LocalDate.now(),
            securityId = "test",
            name = "안유진",
            status = TaxiDriverStatus.WAITING,
            gender = Gender.FEMALE,
            address = address1,
            img = "1111"
        )
        val taxi2 = Taxi(
            carNo = "62거4811",
            vin = "KMHDL41BP8A000001",
            kind = ChargeType.NORMAL,
            manufactureDate = LocalDate.now(),
            fuel = FuelType.GASOLINE,
            color = "white",
            insureYN = true,
            accidentYN = true,
        )
        val address2 = RoadAddress(
            addressName = "address_name_two",
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
        val taxiDriver2 = TaxiDriver(
            taxi = taxi2,
            driverLicenseId = "11",
            issueDate = LocalDate.now(),
            securityId = "test222",
            name = "아이브안유진",
            status = TaxiDriverStatus.WAITING,
            gender = Gender.FEMALE,
            address = address2,
            img = "sopretty"
        )

        // when: taxiDriver1 정보 저장
        val requestTaxiDriver1 = AddTotalInfoRequest(
            taxi = taxiDriver1.taxi,
            driverLicenseId = taxiDriver1.driverLicenseId,
            issueDate = taxiDriver1.issueDate,
            securityId = taxiDriver1.securityId,
            name = taxiDriver1.name,
            status = taxiDriver1.status,
            gender = taxiDriver1.gender,
            address = taxiDriver1.address,
            img = taxiDriver1.img
        )
        taxiDriverService.saveTotalInfo(requestTaxiDriver1.toEntity())

        // then: taxiDriver1과 driverLicenseId가 동일한 taxiDriver2의 정보 등록 요청은 실패한다
        val requestTaxiDriver2 = AddTotalInfoRequest(
            taxi = taxiDriver2.taxi,
            driverLicenseId = taxiDriver2.driverLicenseId,
            issueDate = taxiDriver2.issueDate,
            securityId = taxiDriver2.securityId,
            name = taxiDriver2.name,
            status = taxiDriver2.status,
            gender = taxiDriver2.gender,
            address = taxiDriver2.address,
            img = taxiDriver2.img
        )
        assertThrows<IllegalArgumentException> {
            taxiDriverService.saveTotalInfo(requestTaxiDriver2.toEntity())
        }.apply {
            ExceptionMessage.DUPLICATED_DATA_ALREADY_EXISTS.name
        }
    }

    ///////////////////////////////////////////////////////////
    @Test
    fun `드라이버 상태를 조회한다`() {
        // given: 드라이버 정보
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
        val taxiDriver = TaxiDriver(
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

        // when: taxiDriver의 id로 드라이버 상태 조회
        val requestTaxiDriverId = taxiDriver.id
        val responseTaxiDriver = taxiDriverService.getStatus(requestTaxiDriverId)

        // then: responseTaxiDriver의 상태와 taxiDriver의 상태가 일치하는지 확인
        assertThat(responseTaxiDriver.status).isEqualTo(taxiDriver.status)
    }

    @Test
    fun `존재하지 않는 id로 드라이버를 조회하면 실패한다`() {
        // given: 존재하지 않는 taxiDriverId
        val taxiDriverId = 11111111111111

        // then: 존재하지 않는 taxiDriverId로 taxiDriver를 조회할 수 없다
        assertThrows<IllegalArgumentException> {
            taxiDriverService.getStatus(taxiDriverId)
        }.apply {
            ExceptionMessage.ID_DOES_NOT_EXIST.name
        }
    }

    ///////////////////////////////////////////////////////////
    @Test
    fun `드라이버 상태를 변경한다`() {
        // given: 드라이버 정보
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
        val taxiDriver = TaxiDriver(
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
        // when: 드라이버 상태 변경
        val taxiDeriverId = taxiDriver.id
        val requestTaxiDriver = UpdateStatusRequest(TaxiDriverStatus.STARTDRIVE)
        val responseTaxiDriver = taxiDriverService.updateStatus(taxiDeriverId, requestTaxiDriver)

        // then: responseTaxiDriver의 상태값이 STARTDRIVE로 변경되었는지 확인
        assertThat(responseTaxiDriver.status).isEqualTo(TaxiDriverStatus.STARTDRIVE)
    }

//    @Test
//    fun `존재하지 않는 상태값으로 드라이버 상태를 변경하면 실패한다`() {
//        // given
//        val taxi = Taxi(
//            carNo = "62거4811",
//            vin = "KMHDL41BP8A000001",
//            kind = ChargeType.NORMAL,
//            manufactureDate = LocalDate.now(),
//            fuel = FuelType.GASOLINE,
//            color = "white",
//            insureYN = true,
//            accidentYN = true,
//        )
//        val address = RoadAddress(
//            addressName = "address_name_three",
//            region1depthName = "rg1",
//            region2depthName = "rg2",
//            region3depthName = "rg3",
//            roadName = "road_name",
//            undergroundYn = "Y",
//            mainBuildingNo = "mbNo",
//            subBuildingNo = "subNo",
//            buildingName = "bName",
//            zoneNo = "1",
//            longitude = "x.123",
//            latitude = "y.321",
//        )
//        val taxiDriver = TaxiDriver(
//            taxi = taxi,
//            driverLicenseId = "11",
//            issueDate = LocalDate.now(),
//            securityId = "test",
//            name = "안유진",
//            status = TaxiDriverStatus.WAITING,
//            gender = Gender.FEMALE,
//            address = address,
//            img = "1111"
//        )
//
//        // then: 존재하지 않는 상태값으로 taxiDriver의 상태를 변경할 수 없다
//        val taxiDeriverId = taxiDriver.id
//        val requestTaxiDriver = UpdateStatusRequest("ACCIDENT")
//        val responseTaxiDriver = taxiDriverService.updateStatus(taxiDeriverId, requestTaxiDriver)
//        assertThrows<IllegalArgumentException> {
//            taxiDriverService.updateStatus(taxiDeriverId, requestTaxiDriver)
//        }.apply {
//            ExceptionMessage.INCORRECT_STATUS_VALUE.name
//        }
//    }
}