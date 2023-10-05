package com.hh2.katj.payment.model.entity

import com.hh2.katj.util.model.BaseEntity
import com.hh2.katj.user.model.entity.User
import jakarta.persistence.*
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import org.jetbrains.annotations.NotNull
import java.time.LocalDateTime
import kotlin.math.exp

@Entity
class PaymentMethod (
        paymentType: PaymentType,
        isDefault: Boolean,
        user: User,
        bankAccountNumber: String?,
        bankName: Bank?,
        cardNumber: String?,
        expiryDate: LocalDateTime?,
        cvv: String?,
        cardHolderName: String?,
        isValid: Boolean?,
) : BaseEntity(){
        @Column(nullable = false)
        @Enumerated(EnumType.STRING)
        var paymentType: PaymentType = paymentType
                protected set

        @Column(nullable = false)
        var isDefault: Boolean = isDefault
                protected set

        @ManyToOne
        @JoinColumn(name = "user_id", nullable = false,
                foreignKey = ForeignKey(ConstraintMode.NO_CONSTRAINT))
        var user: User = user
                protected set

        var bankAccountNumber: String? = bankAccountNumber
                protected set

        @Enumerated(EnumType.STRING)
        var bankName: Bank? = bankName
                protected set
        var cardNumber: String? = cardNumber
                protected set
        var expiryDate: LocalDateTime? = expiryDate
                protected set
        var cvv: String? = cvv
                protected set
        var cardHolderName: String? = cardHolderName
                protected set
        var isValid: Boolean? = isValid
                protected set

        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null

}