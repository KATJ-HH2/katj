package com.hh2.katj.taxidriver.component

import com.hh2.katj.taxidriver.model.TaxiDriver
import com.hh2.katj.taxidriver.model.TaxiDriverStatus
import com.hh2.katj.taxidriver.repository.TaxiDriverRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class TaxiDriverReader(
    private val taxiDriverRepository: TaxiDriverRepository,
) {

    @Transactional
    fun findWaitingTaxiDriver(): List<TaxiDriver> {
        val taxiDrivers = taxiDriverRepository.findByStatus(status = TaxiDriverStatus.WAITING)
        return taxiDrivers
    }

}