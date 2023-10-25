package com.hh2.katj.taxi.service

import com.hh2.katj.taxi.component.TaxiManager
import com.hh2.katj.taxi.component.TaxiReader
import com.hh2.katj.taxi.model.Taxi
import com.hh2.katj.taxi.model.dto.response.ResponseAddTaxi
import com.hh2.katj.taxi.model.dto.response.FindTaxiResponse
import com.hh2.katj.util.exception.DataNotFoundException
import org.springframework.stereotype.Service

@Service
class TaxiService(
    private val taxiManager: TaxiManager,
    private val taxiReader: TaxiReader
) {
    fun addTaxi(taxi: Taxi): ResponseAddTaxi {
        return taxiManager.addTaxi(taxi)
    }

    fun findTaxi(id: Long): FindTaxiResponse {
        val findTaxi = taxiReader.findTaxi(id) ?: throw DataNotFoundException(" no taxi ")
        return FindTaxiResponse(findTaxi.id, findTaxi.carNo, findTaxi.fuel, findTaxi.color)
    }
}