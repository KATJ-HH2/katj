package com.hh2.katj.trip.repository

import com.hh2.katj.trip.model.Trip
import org.springframework.data.jpa.repository.JpaRepository

interface TripRepository: JpaRepository<Trip, Long>

//interface TripRepository: JpaRepository<Trip, Long> {
//    fun findTripByTripId(tripId: Long): Trip? {
//        TODO("Not yet implemented")
//    }
//}