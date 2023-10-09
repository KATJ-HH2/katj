package com.hh2.katj.payment.repository

import com.hh2.katj.payment.model.entity.Bank
import com.hh2.katj.payment.model.entity.PaymentMethod
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface PaymentMethodRepository : JpaRepository<PaymentMethod, Long> {

    @Query("SELECT pm FROM PaymentMethod pm WHERE pm.user.id = :userId AND pm.isDefault = true")
    fun findAllByUserIdAndIsDefault(@Param("userId") userId: Long): PaymentMethod?

    @Query("SELECT pm FROM PaymentMethod pm WHERE pm.user.id = :userId AND pm.isDefault = true")
    fun duplicatedDefaultPaymentMethodCheck(@Param("userId") userId: Long): List<PaymentMethod>

    fun findByUserIdAndBankNameAndBankAccountNumber(userId: Long, bankName: Bank?, bankAccountNumber: String?): PaymentMethod?

    fun findByIdAndUserId(userId: Long, paymentMethodId: Long): PaymentMethod?

    fun findAllByUserId(userId: Long): List<PaymentMethod>

    @Modifying
    @Query("DELETE FROM PaymentMethod pm WHERE pm.user.id = :userId")
    fun deleteAllByUserId(@Param("userId") userId: Long?)

    fun findByUserIdAndCardNumberAndCardHolderName(userId: Long, cardNumber: String?, cardHolderName: String?): PaymentMethod?

}