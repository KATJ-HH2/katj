package com.hh2.katj.taxidriver.model.dto.request

import com.fasterxml.jackson.annotation.JsonFormat
import com.hh2.katj.taxi.model.Taxi
import com.hh2.katj.taxidriver.model.entity.TaxiDriver
import com.hh2.katj.taxidriver.model.entity.TaxiDriverStatus
import com.hh2.katj.util.model.Gender
import com.hh2.katj.util.model.RoadAddress
import java.time.LocalDate

data class AddTotalInfoRequest(
    val taxi: Taxi,
    val driverLicenseId: String,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    val issueDate: LocalDate,
    val securityId: String,
    val name: String,
    val status: TaxiDriverStatus,
    val gender: Gender,
    val address: RoadAddress,
    val img: String
) {
    fun toEntity(): TaxiDriver {
        return TaxiDriver(
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
