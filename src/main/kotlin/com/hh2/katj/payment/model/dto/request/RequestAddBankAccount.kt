package com.hh2.katj.payment.model.dto.request

import com.hh2.katj.payment.model.entity.Bank
import com.hh2.katj.payment.model.entity.PaymentMethod
import com.hh2.katj.payment.model.entity.PaymentType
import com.hh2.katj.user.model.entity.User

/**
 * @author : tony
 * @description : 결제 수단 중 계좌 등록 Controller 사용 목적의 DTO
 * @since : 2023-10-09
 */
data class RequestAddBankAccount(
    var bankAccountNumber: String?,
    var bankName: Bank?,

    var isValid: Boolean?,
    var isDefault: Boolean,
) {
    fun toEntity(user: User): PaymentMethod{
        return PaymentMethod(
            paymentType = PaymentType.BANK_ACCOUNT,
            isDefault = this.isDefault,
            user = user,
            bankAccountNumber = this.bankAccountNumber,
            bankName = this.bankName,
            cardNumber = null,
            expiryDate = null,
            cvv = null,
            cardHolderName = null,
            isValid = this.isValid,
        )
    }
}
