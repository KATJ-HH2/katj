package com.hh2.katj.trip.controller

import com.hh2.katj.trip.model.request.RequestTrip
import com.hh2.katj.trip.model.response.ResponseTrip
import com.hh2.katj.trip.service.BillingService
import com.hh2.katj.trip.service.CallingService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * Spring Boot @Bean 으로 등록되면 n초 주기로 계속 확인함
 * @Bean QueueProcessor
 */
@RequestMapping("/trip")
@RestController
class TripController (
    private val billingService: BillingService,
    private val callingService: CallingService,
){

    /**
     * 사용자가 택시 호출을 요청한다
     * trip status CALLING -> WAITING으로 변경 후 반환
     *
     * 스케쥴링 호출이 아닌 직접 호출 해야함
     */
    @PostMapping("/user/request-call-taxi")
    fun callTaxiByUser(@RequestParam request: RequestTrip): ResponseEntity<ResponseTrip> {
        val responseTrip = callingService.callTaxiByUser(request.toEntity())
        return ResponseEntity.ok(responseTrip)
    }

    /**
     * 택시 기사가 사용자에게 결제를 요청하고 결제가 완료되면
     * trip status가 PAY_REQUEST면 결제 진행 후 PAY_COMPLETE로 변경
     */
    @GetMapping("/taxi-driver/request-pay/{tripId}")
    fun taxiDriverRequestPayToUser(@RequestParam userId: Long, @PathVariable tripId: Long): ResponseEntity<ResponseTrip> {
        val responseTrip = billingService.userPayWithRegiPaymentMethod(userId, tripId)
        return ResponseEntity.ok(responseTrip)
    }

    /**
     * 사용자가 해당 택시 이용 기록을 조회한다
     */
    @GetMapping("/search/end-trip/{tripId}")
    fun findOneEndTripByUser(@RequestParam userId: Long, @PathVariable tripId: Long) : ResponseEntity<ResponseTrip> {
        val responseTrip = callingService.findOneEndTripByUser(userId, tripId)
        return ResponseEntity.ok(responseTrip)
    }

    /**
     * 사용자가 모든 택시 이용 기록을 조회한다
     */
    @GetMapping("/search/end-trip")
    fun findAllEndTripByUser(@RequestParam userId: Long) : ResponseEntity<List<ResponseTrip>> {
        val responseTripList = callingService.findAllEndTripByUser(userId)
        return ResponseEntity.ok(responseTripList)
    }




}