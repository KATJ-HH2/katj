package com.hh2.katj.payment.model.entity

import com.hh2.katj.util.model.BaseEntity
import com.hh2.katj.user.model.entity.User
import jakarta.persistence.*
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import org.jetbrains.annotations.NotNull
import java.time.LocalDateTime

@Entity
@DynamicInsert
@DynamicUpdate
class PaymentMethod (

        @Column(nullable = false)
        @Enumerated(EnumType.STRING)
        private var paymentType: PaymentType,

        @Column(nullable = false)
        private var isDefault: Boolean,


        private var bankAccountNumber: String?,

        @Enumerated(EnumType.STRING)
        private var bankName: Bank?,
        private var cardNumber: String?,

        private var expiryDate: LocalDateTime?,

        private var cvv: String?,
        private var cardHolderName: String?,
        private var isValid: Boolean?,

        @ManyToOne
        @JoinColumn(name = "user_id", nullable = false,
                foreignKey = ForeignKey(ConstraintMode.NO_CONSTRAINT))
        private var user: User,

        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        private var id: Long? = null


): BaseEntity(){

}