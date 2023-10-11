package com.hh2.katj.trip.model.response

import com.hh2.katj.taxidriver.model.TaxiDriver
import com.hh2.katj.trip.model.DepartureRoadAddress
import com.hh2.katj.trip.model.DestinationRoadAddress
import com.hh2.katj.trip.model.TripStatus
import com.hh2.katj.user.model.entity.User
import java.time.LocalDate
import java.time.LocalDateTime

data class ResponseTrip(
    val user: User,
    val taxiDriver: TaxiDriver,
    val fare: Int,
    val departure: DepartureRoadAddress,
    val destination: DestinationRoadAddress,
    val driveStartDate: LocalDate,
    val driveStartAt: LocalDateTime,
    val driveEndDate: LocalDate?,
    val driveEndAt: LocalDateTime?,
    val spentTime: Int,
    val tripStatus: TripStatus
)
