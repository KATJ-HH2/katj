package com.hh2.katj.payment.model.dto.response

import com.hh2.katj.payment.model.entity.Bank
import com.hh2.katj.user.model.entity.User
import java.time.LocalDate
import java.time.LocalDateTime

/**
 * @author : tony
 * @description : 결제 수단 조회 후 Controller 반환 목적의 DTO
 * @since : 2023-10-09
 */
data class ResponsePaymentMethod(
    var id: Long,
    var bankAccountNumber: String?,
    var bankName: Bank?,

    var cardNumber: String?,
    var expiryDate: LocalDate?,
    var cvv: String?,
    var cardHolderName: String?,

    var isValid: Boolean?,
    var isDefault: Boolean?,

    var user: User?,
)
