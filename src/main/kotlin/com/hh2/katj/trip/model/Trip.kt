package com.hh2.katj.trip.model

import com.hh2.katj.common.BaseEntity
import com.hh2.katj.util.model.RoadAddress
import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import jakarta.persistence.Entity
import jakarta.persistence.Table
import java.time.LocalDate
import java.time.LocalDateTime
import javax.print.attribute.standard.Destination

@Entity
@Table(name = "TRIP")
class Trip(
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