package com.hh2.katj.trip.component

import com.hh2.katj.trip.model.Trip
import com.hh2.katj.trip.repository.TripRepository
import com.hh2.katj.util.exception.ExceptionMessage
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
    fun findByIdAndUserId(tripId: Long, userId: Long): Trip {
        return tripRepository.findByIdAndUserId(tripId, userId) ?:
                failWithMessage(ID_DOES_NOT_EXIST.name)
    }
}