package com.hh2.katj.taxidriver.service

import com.hh2.katj.taxidriver.component.TaxiDriverManager
import com.hh2.katj.taxidriver.model.dto.request.UpdateStatusRequest
import com.hh2.katj.taxidriver.model.dto.response.TaxiDriverResponse
import com.hh2.katj.taxidriver.model.entity.TaxiDriver
import com.hh2.katj.taxidriver.model.entity.TaxiDriverStatus
import org.springframework.stereotype.Service

@Service
class TaxiDriverService(
    private val taxiDriverManager: TaxiDriverManager
) {
    /**
     * taxiDriverId로 taxiDriver 객체 반환
     **/
    fun checkValidation(taxiDriverId: Long): TaxiDriver {
        val validatedTaxiDriver = taxiDriverManager.findByTaxiDriverId(taxiDriverId)
        return validatedTaxiDriver
    }

    /**
     * 기사님 정보 저장
     **/
    fun saveTotalInfo(request: TaxiDriver): TaxiDriverResponse {
        val savedTotalInfo = taxiDriverManager.saveTotalInfo(request)
        return savedTotalInfo.toResponseDto()
    }

    /**
     * 기사님 상태 조회
     **/
    fun getStatus(taxiDriverId: Long): Enum<TaxiDriverStatus> {
        val status = taxiDriverManager.findStatus(taxiDriverId)
        return status
    }

    /**
     * 기사님 상태 변경
     **/
    fun updateStatus(taxiDriverId: Long, request: UpdateStatusRequest): TaxiDriverResponse {
        val taxiDriver = checkValidation(taxiDriverId)
        val updatedTaxiDriver = taxiDriverManager.updateStatus(taxiDriver.id, request.toEntity(taxiDriver))
        return updatedTaxiDriver.toResponseDto()
    }
}