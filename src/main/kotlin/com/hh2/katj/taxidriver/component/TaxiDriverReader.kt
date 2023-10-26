package com.hh2.katj.taxidriver.component

import com.hh2.katj.taxidriver.model.entity.TaxiDriver
import com.hh2.katj.taxidriver.model.entity.TaxiDriverStatus
import com.hh2.katj.taxidriver.repository.TaxiDriverRepository
import com.hh2.katj.util.exception.ExceptionMessage
import org.springframework.retry.annotation.Backoff
import org.springframework.retry.annotation.Retryable
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class TaxiDriverReader(
    private val taxiDriverRepository: TaxiDriverRepository,
) {

    @Retryable(
        value = [java.lang.RuntimeException::class],
        maxAttempts = 5,
        backoff = Backoff(delay = 1000),
    )
    @Transactional
    fun findWaitingTaxiDriver(): TaxiDriver {
        val taxiDrivers = taxiDriverRepository.findByStatus(status = TaxiDriverStatus.WAITING)
        if (taxiDrivers.isEmpty()) { throw java.lang.RuntimeException(ExceptionMessage.NO_SUCH_VALUE_EXISTS.name) }
        val taxiDriver = taxiDrivers.random()
        return taxiDriver
    }

}