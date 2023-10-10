package com.hh2.katj.trip.model

import com.hh2.katj.util.model.BaseEntity
import com.hh2.katj.taxidriver.model.TaxiDriver
import com.hh2.katj.user.model.entity.User
import com.hh2.katj.util.model.RoadAddress
import jakarta.persistence.*
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(name = "TRIP")
class Trip(
    user: User,
    taxiDriver: TaxiDriver,
    fare: Int,
    departure: RoadAddress,
    destination: RoadAddress,
    driveStartDate: LocalDate,
    driveStartAt: LocalDateTime,
    driveEndDate: LocalDate? = null,
    driveEndAt: LocalDateTime? = null,
    spentTime: LocalDateTime,
    tripStatus: TripStatus
): BaseEntity() {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: User = user

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "taxi_driver_id")
    val taxiDriver: TaxiDriver = taxiDriver

    @Column(nullable = false)
    val fare: Int = fare

    @Column(nullable = false)
    val departure: RoadAddress = departure

    @Column(nullable = false)
    val destination: RoadAddress = destination

    @Column(nullable = false)
    val driveStartDate: LocalDate = driveStartDate

    @Column(nullable = false)
    val driveStartAt: LocalDateTime = driveStartAt

    val driveEndDate: LocalDate? = driveEndDate

    val driveEndAt: LocalDateTime? = driveEndAt

    @Column(nullable = false)
    val spentTime: LocalDateTime = spentTime

    @Column(nullable = false)
    var tripStatus: TripStatus = tripStatus
}