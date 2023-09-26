package com.hh2.katj.payment.etc

enum class PaymentType(paymentType: String) {
    BANK_ACCOUNT("은행 계좌"),
    CREDIT_CARD("신용 카드"),
    DEBIT_CARD("체크 카드")
}