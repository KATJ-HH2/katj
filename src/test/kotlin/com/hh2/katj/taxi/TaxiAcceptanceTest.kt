package com.hh2.katj.taxi

import com.hh2.katj.taxi.fixtures.TaxiTestFixtures.Companion.택시_생성
import com.hh2.katj.taxi.model.ChargeType
import com.hh2.katj.taxi.model.FuelType
import com.hh2.katj.taxi.repository.TaxiRepository
import com.hh2.katj.util.annotation.KATJTestContainerE2E
import com.hh2.katj.util.model.BaseTestEntity
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.time.LocalDate

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

        Assertions.assertThat(택시).isEqualTo("1")
    }
}