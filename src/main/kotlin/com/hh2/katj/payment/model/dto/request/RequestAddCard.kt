package com.hh2.katj.payment.model.dto.request

import com.fasterxml.jackson.annotation.JsonFormat
import com.hh2.katj.payment.model.entity.PaymentMethod
import com.hh2.katj.payment.model.entity.PaymentType
import com.hh2.katj.user.model.entity.User
import java.time.LocalDateTime

data class RequestAddCard(
    var cardNumber: String?,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    var expiryDate: LocalDateTime?,

    var cvv: String?,
    var cardHolderName: String?,

    var isDefault: Boolean,
    var isValid: Boolean?,
) {
    fun toEntity(user: User): PaymentMethod {
        return PaymentMethod(
            paymentType = PaymentType.BANK_ACCOUNT,
            isDefault = this.isDefault,
            user = user,
            bankAccountNumber = null,
            bankName = null,
            cardNumber = this.cardNumber,
            expiryDate = this.expiryDate,
            cvv = this.cvv,
            cardHolderName = this.cardHolderName,
            isValid = this.isValid,
        )
    }
}
