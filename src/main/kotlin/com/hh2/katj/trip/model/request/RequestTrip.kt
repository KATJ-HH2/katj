package com.hh2.katj.trip.model.request

import com.hh2.katj.taxidriver.model.entity.TaxiDriver
import com.hh2.katj.trip.model.DepartureRoadAddress
import com.hh2.katj.trip.model.DestinationRoadAddress
import com.hh2.katj.trip.model.Trip
import com.hh2.katj.trip.model.TripStatus
import com.hh2.katj.user.model.entity.User
import java.time.LocalDate
import java.time.LocalDateTime

data class RequestTrip (
    val user: User,
    val taxiDriver: TaxiDriver?,
    val fare: Int,
    val departure: DepartureRoadAddress,
    val destination: DestinationRoadAddress,
    val driveStartDate: LocalDate,
    val driveStartAt: LocalDateTime,
    val driveEndDate: LocalDate? = null,
    val driveEndAt: LocalDateTime? = null,
    val spentTime: Int,
    val tripStatus: TripStatus
) {
    fun toEntity(): Trip {
        return Trip(
            user = this.user,
            taxiDriver = this.taxiDriver,
            fare = this.fare,
            departure = this.departure,
            destination = this.destination,
            driveStartDate = this.driveStartDate,
            driveStartAt = this.driveStartAt,
            driveEndDate = this.driveEndDate,
            driveEndAt = this.driveEndAt,
            spentTime = this.spentTime,
            tripStatus = this.tripStatus,
        )
    }

}