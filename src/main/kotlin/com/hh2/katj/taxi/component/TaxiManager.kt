package com.hh2.katj.taxi.component

import com.hh2.katj.taxi.model.Taxi
import com.hh2.katj.taxi.model.dto.response.ResponseAddTaxi
import com.hh2.katj.taxi.repository.TaxiRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class TaxiManager(
    private val taxiRepository: TaxiRepository
) {
    @Transactional
    fun addTaxi(taxi: Taxi): ResponseAddTaxi {
        return ResponseAddTaxi(taxiRepository.save(taxi).id)
    }
}