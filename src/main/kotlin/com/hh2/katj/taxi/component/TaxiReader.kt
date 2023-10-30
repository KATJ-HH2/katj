package com.hh2.katj.taxi.component

import com.hh2.katj.taxi.model.Taxi
import com.hh2.katj.taxi.repository.TaxiRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class TaxiReader(
    private val taxiRepository: TaxiRepository
) {
    fun findTaxi(id: Long): Taxi? {
        return taxiRepository.findByIdOrNull(id)
    }
}