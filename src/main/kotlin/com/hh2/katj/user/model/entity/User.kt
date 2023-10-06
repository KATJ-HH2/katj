package com.hh2.katj.user.model.entity

import com.hh2.katj.util.model.BaseEntity
import com.hh2.katj.util.model.RoadAddress
import com.hh2.katj.favorite.model.entity.Favorite
import com.hh2.katj.payment.model.entity.PaymentMethod
import jakarta.persistence.*
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate


@Entity(name = "users")
class User (
        name: String,
        phoneNumber: String,
        email: String,
        gender: Gender,
        roadAddress: RoadAddress,
        status: UserStatus,
) : BaseEntity(){

        @Column(nullable = false)
        var name: String = name
                protected set

        @Column(nullable = false)
        var phoneNumber: String = phoneNumber
                protected set

        @Column(nullable = false)
        var email: String = email
                protected set

        @Column(nullable = false)
        @Enumerated(EnumType.STRING)
        var gender: Gender = gender
                protected set

        @Embedded
        var roadAddress: RoadAddress = roadAddress
                protected set

        var status: UserStatus = status
                protected set

        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null

        override fun equals(other: Any?): Boolean {
                if (this === other) return true
                if (javaClass != other?.javaClass) return false

                other as User

                if (name != other.name) return false
                if (phoneNumber != other.phoneNumber) return false
                if (email != other.email) return false
                if (gender != other.gender) return false
                if (roadAddress != other.roadAddress) return false
                if (status != other.status) return false
                if (id != other.id) return false

                return true
        }

        override fun hashCode(): Int {
                var result = name.hashCode()
                result = 31 * result + phoneNumber.hashCode()
                result = 31 * result + email.hashCode()
                result = 31 * result + gender.hashCode()
                result = 31 * result + roadAddress.hashCode()
                result = 31 * result + status.hashCode()
                result = 31 * result + (id?.hashCode() ?: 0)
                return result
        }


}