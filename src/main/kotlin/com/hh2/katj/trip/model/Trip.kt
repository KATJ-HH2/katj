package com.hh2.katj.trip.model

import com.hh2.katj.taxidriver.model.entity.TaxiDriver
import com.hh2.katj.util.model.BaseEntity
import com.hh2.katj.trip.model.response.ResponseTrip
import com.hh2.katj.user.model.entity.User
import jakarta.persistence.*
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(name = "TRIP")
class Trip(
    user: User,
    taxiDriver: TaxiDriver?,
    fare: Int,
    departure: DepartureRoadAddress,
    destination: DestinationRoadAddress,
    driveStartDate: LocalDate,
    driveStartAt: LocalDateTime,
    driveEndDate: LocalDate? = null,
    driveEndAt: LocalDateTime? = null,
    spentTime: Int,
    tripStatus: TripStatus
): BaseEntity() {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false,
        foreignKey = ForeignKey(ConstraintMode.NO_CONSTRAINT))
    val user: User = user

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "taxi_driver_id", nullable = true,
        foreignKey = ForeignKey(ConstraintMode.NO_CONSTRAINT))
    var taxiDriver: TaxiDriver? = taxiDriver

    @Column(nullable = false)
    val fare: Int = fare

    @Column(nullable = false)
    @Embedded
    val departure: DepartureRoadAddress = departure

    @Column(nullable = false)
    @Embedded
    val destination: DestinationRoadAddress = destination

    val driveStartDate: LocalDate = driveStartDate

    @Column(nullable = false)
    val driveStartAt: LocalDateTime = driveStartAt

    val driveEndDate: LocalDate? = driveEndDate

    val driveEndAt: LocalDateTime? = driveEndAt

    @Column(nullable = false)
    val spentTime: Int? = spentTime

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var tripStatus: TripStatus = tripStatus


    fun toResponseDto(): ResponseTrip {
        return ResponseTrip(
            userId = this.user.id,
            taxiDriverId = this.taxiDriver?.id,
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

    fun updateStatus(payComplete: TripStatus) {
        this.tripStatus = payComplete
    }

    fun assignTaxiDriver(taxiDriver: TaxiDriver?) {
        this.taxiDriver = taxiDriver
        this.tripStatus = TripStatus.ASSIGN_TAXI
    }

}