package com.hh2.katj.taxidriver.model.dto.response

import com.hh2.katj.taxi.model.Taxi
import com.hh2.katj.taxidriver.model.entity.TaxiDriverStatus
import com.hh2.katj.util.model.Gender
import com.hh2.katj.util.model.RoadAddress
import java.time.LocalDate

data class TaxiDriverResponse(
    val id: Long,
    val taxi: Taxi,
    val driverLicenseId: String,
    val issueDate: LocalDate,
    val securityId: String,
    val name: String,
    val status: TaxiDriverStatus,
    val gender: Gender,
    val address: RoadAddress,
    val img: String
)