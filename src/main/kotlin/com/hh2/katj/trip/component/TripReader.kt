package com.hh2.katj.trip.component

import com.hh2.katj.trip.model.Trip
import com.hh2.katj.trip.model.TripStatus
import com.hh2.katj.trip.repository.TripRepository
import com.hh2.katj.util.exception.ExceptionMessage.ID_DOES_NOT_EXIST
import com.hh2.katj.util.exception.failWithMessage
import com.hh2.katj.util.exception.findByIdOrThrowMessage
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class TripReader (
    private val tripRepository: TripRepository,
){
    @Transactional(readOnly = true)
    fun findById(tripId: Long): Trip {
        val findTrip = tripRepository.findByIdOrThrowMessage(tripId, ID_DOES_NOT_EXIST.name)
        return findTrip
    }

    @Transactional(readOnly = true)
    fun findOneEndTripByUser(userId: Long, tripId: Long): Trip {
        return tripRepository.findByIdAndUserIdAndTripStatus(tripId, userId, TripStatus.END) ?:
                failWithMessage(ID_DOES_NOT_EXIST.name)
    }

    @Transactional(readOnly = true)
    fun findAllEndTripByUserId(userId: Long): List<Trip>{
        return tripRepository.findAllByUserIdAndTripStatus(userId, TripStatus.END)
    }

    @Transactional(readOnly = true)
    fun findByIdAndUserId(tripId: Long, userId: Long): Trip {
        return tripRepository.findByIdAndUserId(tripId, userId) ?:
        failWithMessage(ID_DOES_NOT_EXIST.name)
    }

    @Transactional(readOnly = true)
    fun findTripInfoByTaxiDriverId(taxiDriverId: Long): List<Any> {
        val trip = tripRepository.findByTaxiDriverIdAndTripStatus(taxiDriverId, TripStatus.ASSIGN_TAXI)
        val departure = trip?.departure?.departureAddressName?: String
        val destination = trip?.destination?.destinationAddressName?: String
        val fare = trip?.fare?: Int
        val tripInfo = listOf(departure, destination, fare)
        return tripInfo
    }
}