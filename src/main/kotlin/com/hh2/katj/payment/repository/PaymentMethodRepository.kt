package com.hh2.katj.payment.repository

import com.hh2.katj.payment.model.PaymentMethod
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface PaymentMethodRepository: JpaRepository<PaymentMethod, Long> {
}