package com.hh2.katj.trip.component

import com.hh2.katj.trip.model.Trip
import com.hh2.katj.trip.repository.TripRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Transactional(readOnly = true)
class TripReader (
    private val tripRepository: TripRepository
){
    fun findByTripIdWithException(tripId: Long): Trip {
        return tripRepository.findByIdOrNull(tripId)
            ?: throw NotFoundException()
    }
}