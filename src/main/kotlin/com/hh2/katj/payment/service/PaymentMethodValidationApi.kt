package com.hh2.katj.payment.service

import com.hh2.katj.payment.model.entity.PaymentMethod
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class PaymentMethodValidationApi {

    @Value("\${spring.profiles.active}")
    private val activeProfile: String? = null

    fun bankAccountValidation(paymentMethod: PaymentMethod): Boolean{
        /**
         * 외부 API 사용으로 요청 계좌 정보 유효성 확인 로직 작성
         */
        paymentMethod.changeValidToTrue()
        return activeProfile.isLocal()
    }

    fun cardValidation(paymentMethod: PaymentMethod): Boolean {
        /**
         * 외부 API 사용으로 요청 카드 정보 유효성 확인 로직 작성
         */
        paymentMethod.changeValidToTrue()
        return activeProfile.isLocal()
    }

}

private fun String?.isLocal(): Boolean {
    return this.equals("local")
}
