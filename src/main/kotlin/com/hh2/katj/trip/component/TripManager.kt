package com.hh2.katj.trip.component

import com.hh2.katj.taxidriver.model.TaxiDriver
import com.hh2.katj.trip.model.Trip
import com.hh2.katj.trip.model.TripStatus
import com.hh2.katj.trip.repository.TripRepository
import com.hh2.katj.util.exception.ExceptionMessage
import com.hh2.katj.util.exception.failWithMessage
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class TripManager(
    private val tripRepository: TripRepository,
) {

    @Transactional
    fun updateTripStatusToPayComplete(userId:Long, tripId: Long): Trip {
        val findTrip = tripExistValidation(userId, tripId)

        try {
            findTrip.updateStatus(TripStatus.END)
        } catch (e: Exception) {
            throw Exception(ExceptionMessage.INTERNAL_SERVER_ERROR_FROM_DATABASE.name)
        }

        return findTrip
    }

    @Transactional
    fun callTaxiByUser(request: Trip): Trip {
        return tripRepository.save(request)
    }

    @Transactional
    fun assignTaxiDriver(trip: Trip, taxiDriver: TaxiDriver) {
        trip.assignTaxiDriver(taxiDriver)
    }

    /**
     * 해당 사용자의 해당 trip 존재 검증
     */
    private fun tripExistValidation(userId: Long, tripId: Long): Trip =
        tripRepository.findByIdAndUserId(tripId, userId)
            ?: failWithMessage(ExceptionMessage.ID_DOES_NOT_EXIST.name)

}