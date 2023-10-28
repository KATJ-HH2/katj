package com.hh2.katj.taxidriver.service

import com.hh2.katj.taxi.model.ChargeType
import com.hh2.katj.taxi.model.FuelType
import com.hh2.katj.taxi.model.Taxi
import com.hh2.katj.taxi.repository.TaxiRepository
import com.hh2.katj.util.annotation.KATJTestContainerE2E
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestConstructor
import java.time.LocalDate

@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class TaxiDriverService(
    private val taxiRepository: TaxiRepository,
) {

    @Test
    fun `택시 저장`(){
        val taxi: Taxi = Taxi(
            carNo = "carNUm",
            vin = "vin",
            kind = ChargeType.NORMAL,
            manufactureDate = LocalDate.now().minusYears(2),
            fuel = FuelType.DIESEL,
            color = "white",
            insureYN = true,
            accidentYN = false,
        )

        taxiRepository.save(taxi)
    }

}