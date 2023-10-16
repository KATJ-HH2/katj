package com.hh2.katj.taxidriver.controller

import com.fasterxml.jackson.annotation.JsonProperty
import com.hh2.katj.taxidriver.model.dto.request.AddTotalInfoRequest
import com.hh2.katj.taxidriver.model.dto.response.TaxiDriverResponse
import com.hh2.katj.taxidriver.service.TaxiDriverService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/taxidriver")
@RestController
class TaxiDriverController(
    private val taxiDriverService: TaxiDriverService
) {
    /**
     * 기사님 정보 등록
     */
    @PostMapping("/enroll")
    fun addTotalInfo(@RequestBody request: AddTotalInfoRequest): ResponseEntity<TaxiDriverResponse> {
//        val taxiDriver = taxiDriverService.findByTaxiDriverId(taxiDriverId)
        val savedTotalInfo = taxiDriverService.saveTotalInfo(request.toEntity())
        return ResponseEntity.ok(savedTotalInfo)
    }
}