package com.hh2.katj.taxi

import com.hh2.katj.taxi.fixtures.TaxiTestFixtures.Companion.택시_생성
import com.hh2.katj.taxi.fixtures.TaxiTestFixtures.Companion.택시_조회
import com.hh2.katj.taxi.model.ChargeType
import com.hh2.katj.taxi.model.FuelType
import com.hh2.katj.taxi.repository.TaxiRepository
import com.hh2.katj.util.annotation.KATJTestContainerE2E
import com.hh2.katj.util.model.BaseTestEntity
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.function.Executable
import java.time.LocalDate
import org.junit.jupiter.api.Assertions as JupiterApiAssertions

@KATJTestContainerE2E
class TaxiAcceptanceTest(
    private val taxiRepository: TaxiRepository
): BaseTestEntity() {

    @BeforeEach
    fun setUp() {
        taxiRepository.deleteAllInBatch()
    }

    /**
     * given:
     * when: 새로운 택시 생성하면
     * then: 생성한 택시를 조회 할 수 있다.
     */
    @DisplayName("택시 생성")
    @Test
    fun createTaxi() {
        //given
        //when
        val 택시 = 택시_생성(
            "62거4811",
            "KMHDL41BP8A000001",
            ChargeType.NORMAL,
            LocalDate.now(),
            FuelType.LPG,
            "YELLOW",
            true,
            false
        )

        val cardNo = 택시.jsonPath().getString("carNo")

        //then
        Assertions.assertThat(cardNo).isEqualTo("62거4811")
    }

    /**
     * given: 62가1111번호, 연료타입이 가솔린인 택시를 생성
     * when: 새롭게 생성한 택시를 조회하면
     * then: 번호가 62가1111이고, 연료가 가솔린이다.
     */
    @DisplayName("택시 조회")
    @Test
    fun getTaxi() {
        //given
        val 택시 = 택시_생성(
            "62거1111",
            "KMHDL41BP8A000001",
            ChargeType.NORMAL,
            LocalDate.now(),
            FuelType.GASOLINE,
            "RED",
            true,
            false
        )

        //when
        val response = 택시_조회(택시)

        //then
        JupiterApiAssertions.assertAll(
            Executable { JupiterApiAssertions.assertEquals("62거1111", response.jsonPath().getString("carNo")) },
            Executable { JupiterApiAssertions.assertEquals(FuelType.GASOLINE.toString(), response.jsonPath().getString("fuelType")) }
        )
    }
}