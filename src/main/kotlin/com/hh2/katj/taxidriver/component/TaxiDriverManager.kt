package com.hh2.katj.taxidriver.component

import com.hh2.katj.taxi.model.Taxi
import com.hh2.katj.taxidriver.model.dto.request.AddTotalInfoRequest
import com.hh2.katj.taxidriver.model.entity.TaxiDriver
import com.hh2.katj.taxidriver.model.entity.TaxiDriverStatus
import com.hh2.katj.taxidriver.repository.TaxiDriverRepository
import com.hh2.katj.util.exception.ExceptionMessage.USER_DOES_NOT_EXIST
import com.hh2.katj.util.exception.ExceptionMessage.INTERNAL_SERVER_ERROR_FROM_DATABASE
import com.hh2.katj.util.exception.findByIdOrThrowMessage
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class TaxiDriverManager(
    private val taxiDriverRepository: TaxiDriverRepository
) {
    @Transactional(readOnly = true)
    fun findByTaxiDriverId(taxiDriverId: Long): TaxiDriver =
        taxiDriverRepository.findByIdOrThrowMessage(taxiDriverId, USER_DOES_NOT_EXIST.name)

    @Transactional
    fun saveTotalInfo(request: TaxiDriver): TaxiDriver {
        val infoAddedTaxiDriver = taxiDriverRepository.save(request)
        return infoAddedTaxiDriver
    }

}