package com.hh2.katj.payment.repository

import com.hh2.katj.payment.model.entity.PaymentMethod
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PaymentMethodRepository: JpaRepository<PaymentMethod, Long> {
}