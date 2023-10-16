package com.hh2.katj.taxidriver.model.dto.request

import com.hh2.katj.taxidriver.model.entity.TaxiDriver
import com.hh2.katj.taxidriver.model.entity.TaxiDriverStatus

data class UpdateStatusRequest(
    val status: TaxiDriverStatus,
) {
    fun toEntity(taxiDriver: TaxiDriver): TaxiDriver {
        return TaxiDriver(
            taxi = taxiDriver.taxi,
            driverLicenseId = taxiDriver.driverLicenseId,
            issueDate = taxiDriver.issueDate,
            securityId = taxiDriver.securityId,
            name = taxiDriver.name,
            status = this.status,
            gender = taxiDriver.gender,
            address = taxiDriver.address,
            img = taxiDriver.img
        )
    }
}
