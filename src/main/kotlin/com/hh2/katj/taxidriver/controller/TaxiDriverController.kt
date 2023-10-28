package com.hh2.katj.taxidriver.controller

import com.hh2.katj.taxidriver.model.dto.request.AddTotalInfoRequest
import com.hh2.katj.taxidriver.model.dto.request.UpdateStatusRequest
import com.hh2.katj.taxidriver.model.dto.response.TaxiDriverResponse
import com.hh2.katj.taxidriver.model.entity.TaxiDriver
import com.hh2.katj.taxidriver.service.TaxiDriverService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
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
        val savedTotalInfo = taxiDriverService.saveTotalInfo(request.toEntity())
        return ResponseEntity.ok(savedTotalInfo)
    }

    /**
     * 기사님 상태 조회
     **/
    @GetMapping("/status")
    fun getStatus(@RequestParam id: Long): ResponseEntity<TaxiDriver> {
        val taxiDriver = taxiDriverService.getStatus(id)
        return ResponseEntity.ok(taxiDriver)
    }

    /**
     * 기사님 상태 변경
     **/
    @PutMapping("/status")
    fun updateStatus(@RequestParam id: Long,
                     @RequestBody request: UpdateStatusRequest): ResponseEntity<TaxiDriverResponse> {
        val statusChangedTaxiDriver = taxiDriverService.updateStatus(id, request)
        return ResponseEntity.ok(statusChangedTaxiDriver)
    }
}

// TODO: controller에서 toEntity 구현 방안 고민
