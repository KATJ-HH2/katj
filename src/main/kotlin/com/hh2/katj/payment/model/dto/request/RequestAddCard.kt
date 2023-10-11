package com.hh2.katj.payment.model.dto.request

import com.fasterxml.jackson.annotation.JsonFormat
import com.hh2.katj.payment.model.entity.PaymentMethod
import com.hh2.katj.payment.model.entity.PaymentType
import com.hh2.katj.user.model.entity.User
import jakarta.validation.constraints.Pattern
import java.time.LocalDateTime

data class RequestAddCard(
    @Pattern(regexp = "\\d{4}-\\d{4}-\\d{4}-\\d{4}", message = "NOT_A_PROPER_CARD_NUMBER")
    var cardNumber: String?,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    var expiryDate: LocalDateTime?,

    @Pattern(regexp = "\\d{3}", message = "MUST_BE_3_DIGIT_NUMBERS")
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
