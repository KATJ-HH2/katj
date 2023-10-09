package com.hh2.katj.taxi_driver.model

import com.hh2.katj.common.BaseEntity
import com.hh2.katj.domain.user.model.Gender
import com.hh2.katj.util.model.RoadAddress
import jakarta.persistence.Column
import jakarta.persistence.Entity
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
class TaxiDriver(
    driverLicenseId: String,
    issueDate: LocalDate,
    securityId: String,
    name: String,
    status: TaxiDriverStatus,
    gender: Gender,
    address: RoadAddress,
    img: String,
    registAt: LocalDateTime
): BaseEntity() {

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