package com.hh2.katj.taxidriver.service

import com.hh2.katj.taxi.fixtures.TaxiTestFixtures
import com.hh2.katj.taxi.model.ChargeType
import com.hh2.katj.taxi.model.FuelType
import com.hh2.katj.taxi.repository.TaxiRepository
import com.hh2.katj.util.annotation.KATJTestContainerE2E
import com.hh2.katj.util.model.BaseTestEntity
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDate

@KATJTestContainerE2E
class TaxiDriverServiceTest(
    private val taxiRepository: TaxiRepository
): BaseTestEntity() {
    @BeforeEach
    fun setUp() {
        taxiRepository.deleteAllInBatch()
    }

    @Test
    fun `택시_저장`(){
        val taxi = TaxiTestFixtures.택시_생성(
            "62거4811",
            "KMHDL41BP8A000001",
            ChargeType.NORMAL,
            LocalDate.now(),
            FuelType.LPG,
            "YELLOW",
            true,
            false
        )
    }

}