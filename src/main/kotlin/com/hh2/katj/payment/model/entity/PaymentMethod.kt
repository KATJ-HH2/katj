package com.hh2.katj.payment.model.entity

import com.hh2.katj.payment.model.dto.response.ResponsePaymentMethod
import com.hh2.katj.user.model.entity.User
import com.hh2.katj.util.model.BaseEntity
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "payment_method")
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

        override fun equals(other: Any?): Boolean {
                if (this === other) return true
                if (javaClass != other?.javaClass) return false

                other as PaymentMethod

                if (paymentType != other.paymentType) return false
                if (isDefault != other.isDefault) return false
                if (user != other.user) return false
                if (bankAccountNumber != other.bankAccountNumber) return false
                if (bankName != other.bankName) return false
                if (cardNumber != other.cardNumber) return false
                if (expiryDate != other.expiryDate) return false
                if (cvv != other.cvv) return false
                if (cardHolderName != other.cardHolderName) return false
                if (isValid != other.isValid) return false

                return true
        }

        override fun hashCode(): Int {
                var result = paymentType.hashCode()
                result = 31 * result + (isDefault?.hashCode() ?: 0)
                result = 31 * result + user.hashCode()
                result = 31 * result + (bankAccountNumber?.hashCode() ?: 0)
                result = 31 * result + (bankName?.hashCode() ?: 0)
                result = 31 * result + (cardNumber?.hashCode() ?: 0)
                result = 31 * result + (expiryDate?.hashCode() ?: 0)
                result = 31 * result + (cvv?.hashCode() ?: 0)
                result = 31 * result + (cardHolderName?.hashCode() ?: 0)
                result = 31 * result + (isValid?.hashCode() ?: 0)
                return result
        }

        fun addPaymentMethodTo(user: User) {
                this.user = user
        }

        fun changeDefaultToFalse() {
                this.isDefault = false
        }

        fun changeDefaultToTrue() {
                this.isDefault = true
        }

        fun changeValidToTrue() {
                this.isValid = true
        }

        fun toResponsePaymentMethod(): ResponsePaymentMethod {
                return ResponsePaymentMethod(
                    id = this.id,
                    bankAccountNumber = this.bankAccountNumber,
                    bankName = this.bankName,
                    cardNumber = this.cardNumber,
                    expiryDate = this.expiryDate,
                    cvv = this.cvv,
                    cardHolderName = this.cardHolderName,
                    isValid = this.isValid,
                    isDefault = this.isDefault,
                    user = this.user,
                )
        }


}