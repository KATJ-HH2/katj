package com.hh2.katj.trip.repository

import com.hh2.katj.trip.model.Trip
import com.hh2.katj.trip.model.TripStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TripRepository: JpaRepository<Trip, Long> {
    fun findByIdAndUserId(tripId: Long, userId: Long): Trip?
    fun findAllByUserId(userId: Long): List<Trip>
    fun findByIdAndUserIdAndTripStatus(tripId: Long, userId: Long, tripStatus: TripStatus): Trip?
    fun findAllByUserIdAndTripStatus(userId: Long, tripStatus: TripStatus): List<Trip>
    fun findByTaxiDriverIdAndTripStatus(taxiDriverId: Long, tripStatus: TripStatus): Trip?
}