package com.hh2.katj.payment.component

import com.hh2.katj.payment.model.entity.PaymentMethod
import com.hh2.katj.payment.repository.PaymentMethodRepository
import com.hh2.katj.user.repository.UserRepository
import com.hh2.katj.util.exception.ExceptionMessage
import com.hh2.katj.util.exception.failWithMessage
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class PaymentMethodReader (
    private val paymentMethodRepository: PaymentMethodRepository,
){


    @Transactional(readOnly = true)
    fun isDefaultExistCheck(userId: Long): PaymentMethod? {
        val isDefaultExistCheck = paymentMethodRepository.findAllByUserIdAndIsDefault(userId)
        return isDefaultExistCheck
    }

    @Transactional(readOnly = true)
    fun duplicatedDefaultPaymentMethodCheck(userId: Long): List<PaymentMethod> =
        paymentMethodRepository.duplicatedDefaultPaymentMethodCheck(userId)

    @Transactional(readOnly = true)
    fun duplicatedBankAccountCheck(userId: Long, paymentMethod: PaymentMethod): Boolean {
        val findBankAccount = paymentMethodRepository.findByUserIdAndBankNameAndBankAccountNumber(userId, paymentMethod.bankName, paymentMethod.bankAccountNumber)
        return findBankAccount == null
    }

    @Transactional(readOnly = true)
    fun findOnePaymentMethod(userId: Long, paymentMethodId: Long): PaymentMethod {
        val findPaymentMethod = paymentMethodExistValidation(userId, paymentMethodId)
        return findPaymentMethod
    }


    /**
     * 결제수단 유효성 체크
     * 해당 유저에게 즐겨찾기가 존재하는지 확인한다
     */
    @Transactional(readOnly = true)
    internal fun paymentMethodExistValidation(userId: Long, paymentMethodId: Long): PaymentMethod =
        paymentMethodRepository.findByIdAndUserId(paymentMethodId, userId)
            ?: failWithMessage(ExceptionMessage.ID_DOES_NOT_EXIST.name)


    @Transactional(readOnly = true)
    fun duplicatedCardCheck(userId: Long, paymentMethod: PaymentMethod): Boolean {
        val findCard = paymentMethodRepository.findByUserIdAndCardNumberAndCardHolderName(userId, paymentMethod.cardNumber, paymentMethod.cardHolderName)
        return findCard == null
    }
}