package com.hh2.katj.payment.component

import com.hh2.katj.payment.model.entity.PaymentMethod
import com.hh2.katj.payment.repository.PaymentMethodRepository
import com.hh2.katj.util.exception.ExceptionMessage
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class PaymentMethodManager (
    private val paymentMethodRepository: PaymentMethodRepository,
    private val paymentMethodReader: PaymentMethodReader,
){

    @Transactional
    fun addPaymentMethod(request: PaymentMethod): PaymentMethod {
        val saveBackAccount = paymentMethodRepository.save(request)
        return saveBackAccount
    }

    @Transactional
    fun changeDefaultToFalse(paymentMethod: PaymentMethod) {
        paymentMethod.changeDefaultToFalse()
        paymentMethodRepository.save(paymentMethod)
    }

    @Transactional
    fun deleteOnePaymentMethod(userId: Long, paymentMethodId: Long): Boolean {
        val deletePaymentMethod = paymentMethodReader.paymentMethodExistValidation(userId, paymentMethodId)

        try {
            paymentMethodRepository.delete(deletePaymentMethod)
        } catch (e: Exception) {
            throw Exception(ExceptionMessage.INTERNAL_SERVER_ERROR_FROM_DATABASE.name)
        }
        return true
    }

    @Transactional
    fun deleteAllFavorite(userId: Long): Boolean {
        try {
            paymentMethodRepository.deleteAllByUserId(userId)
        } catch (e: Exception) {
            throw Exception(ExceptionMessage.INTERNAL_SERVER_ERROR_FROM_DATABASE.name)
        }
        return true
    }
}