package com.hh2.katj.trip.service

import com.hh2.katj.payment.component.PaymentMethodReader
import com.hh2.katj.taxidriver.model.entity.TaxiDriver
import com.hh2.katj.taxidriver.service.TaxiDriverService
import com.hh2.katj.trip.component.TripManager
import com.hh2.katj.trip.component.TripReader
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
    private val tripReader: TripReader,
    private val tripManager: TripManager,
    private val userService: UserService,
    private val paymentMethodReader: PaymentMethodReader,
    private val taxiDriverService: TaxiDriverService,
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

        tripManager.assignTaxiDriver(trip, taxiDriver)

        return taxiDriver
    }

    private fun userValidation(userId: Long): User {
        val findUser = userService.findByUserId(userId)
        return userService.userValidationCheck(findUser.id)
    }

    private fun taxiDriverValidation(taxiDriverId: Long): TaxiDriver {
        val validatedTaxiDriver = taxiDriverService.checkValidation(taxiDriverId)
        return validatedTaxiDriver
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

    fun findTripInfoByTaxiDriverId(taxiDriverId: Long): List<Any> {
        val validatedTaxiDriver = taxiDriverValidation(taxiDriverId)
        val tripInfo = tripReader.findTripInfoByTaxiDriverId(validatedTaxiDriver.id)
        return tripInfo
    }

}
