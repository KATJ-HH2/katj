package com.hh2.katj.trip.model

import com.hh2.katj.taxi.model.Taxi
import com.hh2.katj.taxidriver.model.TaxiDriver
import com.hh2.katj.util.BaseEntity
import com.hh2.katj.util.RoadAddress
import jakarta.persistence.*
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(name = "trip")
class Trip (user: User,
            taxiDriver: TaxiDriver,
            taxi: Taxi,
            paymentMethodId: Long,
            fare: Long,
            departure: RoadAddress,
            destination: RoadAddress,
            driveStartDate: LocalDate,
            driveStartAt: LocalDateTime,
            driveEndDate: LocalDate,
            driveEndAt: LocalDateTime,
            spentTime: LocalDateTime,
            tripStatus: TripStatus
        ): BaseEntity() {
//    @Id BaseEntity를 통해서 생성
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(nullable = false)
//    val id: Long = 0L

    @ManyToOne
    @Column(nullable = false)
    var user: User = user
        protected set

    @ManyToOne
    @Column(nullable = false)
    var taxiDriver: TaxiDriver = taxiDriver
        protected set

    @ManyToOne
    @Column(nullable = false)
    var taxiId: Taxi = taxi
        protected set

    @Column(nullable = false)
    var paymentMethodId: Long = paymentMethodId
        protected set

    @Column(nullable = false)
    var fare: Long = fare
        protected set

    @Column(nullable = false)
    var departure: RoadAddress = departure
        protected set

    @Column(nullable = false)
    var destination: RoadAddress = destination
        protected set

    @Column(nullable = false)
    var driveStartDate: LocalDate = driveStartDate
        protected set

    @Column(nullable = false)
    var driveStartAt: LocalDateTime = driveStartAt
        protected set

    @Column(nullable = false)
    var driveEndDate: LocalDate = driveEndDate
        protected set

    @Column(nullable = false)
    var driveEndAt: LocalDateTime = driveEndAt
        protected set

    @Column(nullable = false)
    var spentTime: LocalDateTime = spentTime
        protected set

    @Column(nullable = false)
    var tripStatus: TripStatus = tripStatus
        protected set
}