package com.hh2.katj.trip.service

import com.hh2.katj.taxidriver.component.TaxiDriverReader
import com.hh2.katj.taxidriver.model.TaxiDriver
import com.hh2.katj.trip.component.TripManager
import com.hh2.katj.trip.model.Trip
import com.hh2.katj.trip.model.response.ResponseTrip
import com.hh2.katj.user.model.entity.User
import com.hh2.katj.user.service.UserService
import com.hh2.katj.util.exception.ExceptionMessage.NO_SUCH_VALUE_EXISTS
import com.hh2.katj.util.exception.failWithMessage
import org.springframework.retry.annotation.Backoff
import org.springframework.retry.annotation.Retryable
import org.springframework.stereotype.Service

@Service
class CallingService (
    private val tripManager: TripManager,
    private val userService: UserService,
    private val taxiDriverReader: TaxiDriverReader,
){

    fun callTaxiByUser(request: Trip): ResponseTrip {
        userValidation(request.user.id)

        val savedTrip = tripManager.callTaxiByUser(request)

        // 택시 랜덤 배정
        assignTaxiDriver(savedTrip)

        return savedTrip.toResponseDto()
    }

    @Retryable(
        include = [RuntimeException::class],
        maxAttempts = 3,
        backoff = Backoff(delay = 1000),
    )
    fun assignTaxiDriver(trip: Trip): TaxiDriver? {
        val taxiDrivers = taxiDriverReader.findWaitingTaxiDriver()
        check(taxiDrivers.isNotEmpty()) { failWithMessage(NO_SUCH_VALUE_EXISTS.name) }
        val taxiDriver = taxiDrivers.random()

        trip.assignTaxiDriver(taxiDriver)

        return taxiDriver
    }

    private fun userValidation(userId: Long): User {
        val findUser = userService.findByUserId(userId)
        return userService.userValidationCheck(findUser.id)
    }

}