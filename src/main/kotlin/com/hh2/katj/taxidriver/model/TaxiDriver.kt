package com.hh2.katj.taxidriver.model

import com.hh2.katj.util.model.BaseEntity
import com.hh2.katj.util.model.Gender
import com.hh2.katj.taxi.model.Taxi
import com.hh2.katj.util.model.RoadAddress
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import java.time.LocalDate

@Entity
class TaxiDriver(
    taxi: Taxi,
    driverLicenseId: String,
    issueDate: LocalDate,
    securityId: String,
    name: String,
    status: TaxiDriverStatus,
    gender: Gender,
    address: RoadAddress,
    img: String
): BaseEntity() {
    @OneToOne
    @JoinColumn(name = "taxi_id")
    val taxi: Taxi = taxi

    @Column(nullable = false)
    val driverLicenseId: String = driverLicenseId

    @Column(nullable = false)
    val issueDate: LocalDate = issueDate

    @Column(nullable = false)
    val securityId: String = securityId

    @Column(nullable = false)
    val name: String = name

    @Column(nullable = false)
    val status: TaxiDriverStatus = status

    @Column(nullable = false)
    val gender: Gender = gender

    @Column(nullable = false)
    val address: RoadAddress = address

    @Column(nullable = false)
    val img: String = img
}