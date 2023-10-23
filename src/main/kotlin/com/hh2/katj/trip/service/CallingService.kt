package com.hh2.katj.trip.service

import com.hh2.katj.taxidriver.component.TaxiDriverReader
import com.hh2.katj.trip.component.TripManager
import com.hh2.katj.trip.component.TripReader
import com.hh2.katj.trip.model.Trip
import com.hh2.katj.trip.model.response.ResponseTrip
import com.hh2.katj.user.model.entity.User
import com.hh2.katj.user.service.UserService
import org.springframework.stereotype.Service

@Service
class CallingService (
    private val tripReader: TripReader,
    private val tripManager: TripManager,
    private val userService: UserService,
    private val taxiDriverReader: TaxiDriverReader,
){

    fun callTaxiByUser(request: Trip): ResponseTrip {
        userValidation(request.user.id)

        val savedTrip = tripManager.callTaxiByUser(request)

        val taxiDriver = taxiDriverReader.findWaitingTaxiDriver()
        tripManager.assignTaxiDriver(savedTrip, taxiDriver)

        return savedTrip.toResponseDto()
    }

    private fun userValidation(userId: Long): User {
        val findUser = userService.findByUserId(userId)
        return userService.userValidationCheck(findUser.id)
    }

    fun findOneEndTripByUser(userId: Long, tripId: Long): ResponseTrip {
        val validatedUser = userValidation(userId)

        val findOneTrip = tripReader.findOneEndTripByUser(validatedUser.id, tripId)
        return findOneTrip.toResponseDto()
    }

    fun findAllEndTripByUser(userId: Long): List<ResponseTrip> {
        val validatedUser = userValidation(userId)

        return tripReader.findAllEndTripByUserId(validatedUser.id).map(Trip::toResponseDto)
    }

}