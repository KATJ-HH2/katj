package com.hh2.katj.trip.controller

import com.hh2.katj.trip.model.response.ResponseTrip
import com.hh2.katj.trip.service.BillingService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/trip")
@RestController
class TripController (
    private val billingService: BillingService,
){

    /**
     * 택시 기사가 사용자에게 결제를 요청하고 결제가 완료되면
     * trip status가 PAY_REQUEST면 결제 진행 후 PAY_COMPLETE로 변경
     */
    @GetMapping("/taxi-driver/request-pay/{tripId}")
    fun taxiDriverRequestPayToUser(@RequestParam userId: Long, @PathVariable tripId: Long): ResponseEntity<ResponseTrip> {
        val responseTrip = billingService.userPayWithRegiPaymentMethod(userId, tripId)
        return ResponseEntity.ok(responseTrip)
    }

}