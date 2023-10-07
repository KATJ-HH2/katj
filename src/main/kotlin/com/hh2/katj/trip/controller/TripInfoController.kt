package com.hh2.katj.trip.controller

import DriverTripService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TripInfoController(
    private val driverTripService: DriverTripService // Service 빈 주입
) {
    @GetMapping("/trip-info")
    fun tripInfo(): String {
        driverTripService.getTripInfo("11")
        return "trip info"

    }
}