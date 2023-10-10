package com.hh2.katj.trip.repository

import com.hh2.katj.trip.model.Trip
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TripRepository: JpaRepository<Trip, Long> {
}