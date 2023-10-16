package com.hh2.katj.taxidriver.model.entity

import com.hh2.katj.util.model.BaseEntity
import com.hh2.katj.util.model.Gender
import com.hh2.katj.taxi.model.Taxi
import com.hh2.katj.taxidriver.model.dto.request.AddTotalInfoRequest
import com.hh2.katj.taxidriver.model.dto.request.UpdateStatusRequest
import com.hh2.katj.taxidriver.model.dto.response.TaxiDriverResponse
import com.hh2.katj.util.model.RoadAddress
import jakarta.persistence.*
import java.time.LocalDate

@Entity
class TaxiDriver(
    status: TaxiDriverStatus,
    taxi: Taxi,
    driverLicenseId: String,
    issueDate: LocalDate,
    securityId: String,
    name: String,
    gender: Gender,
    address: RoadAddress,
    img: String
): BaseEntity() {
    @OneToOne
    @JoinColumn(name = "taxi_id", nullable = false,
        foreignKey = ForeignKey(ConstraintMode.NO_CONSTRAINT))
    var taxi: Taxi = taxi

    @Column(nullable = false)
    var driverLicenseId: String = driverLicenseId

    @Column(nullable = false)
    var issueDate: LocalDate = issueDate

    @Column(nullable = false)
    var securityId: String = securityId

    @Column(nullable = false)
    var name: String = name

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    var status: TaxiDriverStatus = status

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    var gender: Gender = gender

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    var address: RoadAddress = address

    @Column(nullable = false)
    var img: String = img

    fun update(request: TaxiDriver) {
        this.taxi = request.taxi
        this.driverLicenseId = request.driverLicenseId
        this.issueDate = request.issueDate
        this.securityId = request.securityId
        this.name = request.name
        this.status = request.status
        this.gender = request.gender
        this.address = request.address
        this.img = request.img
    }

    fun updateStatus(status: TaxiDriverStatus) {
        this.status = status
    }

    fun toResponseDto(): TaxiDriverResponse {
        return TaxiDriverResponse(
            id = this.id,
            taxi = this.taxi,
            driverLicenseId = this.driverLicenseId,
            issueDate = this.issueDate,
            securityId = this.securityId,
            name = this.name,
            status = this.status,
            gender = this.gender,
            address = this.address,
            img = this.img
        )
    }
}