package com.hh2.katj.taxi.service

import com.hh2.katj.taxi.component.TaxiManager
import com.hh2.katj.taxi.model.Taxi
import com.hh2.katj.taxi.model.dto.response.ResponseAddTaxi
import org.springframework.stereotype.Service

@Service
class TaxiService(
    private val taxiManager: TaxiManager
) {
    fun addTaxi(taxi: Taxi): ResponseAddTaxi {
        return taxiManager.addTaxi(taxi)
    }
}