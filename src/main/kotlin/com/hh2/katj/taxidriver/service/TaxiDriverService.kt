package com.hh2.katj.taxidriver.service

import com.hh2.katj.taxidriver.component.TaxiDriverManager
import com.hh2.katj.taxidriver.model.dto.response.TaxiDriverResponse
import com.hh2.katj.taxidriver.model.entity.TaxiDriver
import com.hh2.katj.taxidriver.model.entity.TaxiDriverStatus
import org.springframework.stereotype.Service

@Service
class TaxiDriverService(
    private val taxiDriverManager: TaxiDriverManager
) {
    fun findByTaxiDriverId(taxiDriverId: Long): TaxiDriver {
        val taxiDriver = taxiDriverManager.findByTaxiDriverId(taxiDriverId)
        return taxiDriver
    }

    fun saveTotalInfo(request: TaxiDriver): TaxiDriverResponse {
        val savedTotalInfo = taxiDriverManager.saveTotalInfo(request)
        return savedTotalInfo.toResponseDto()
    }

}