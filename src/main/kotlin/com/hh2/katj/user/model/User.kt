package com.hh2.katj.user.model

import com.hh2.katj.cmn.model.BaseEntity
import com.hh2.katj.cmn.model.RoadAddress
import com.hh2.katj.favorite.model.Favorite
import com.hh2.katj.payment.model.PaymentMethod
import com.hh2.katj.user.util.Gender
import com.hh2.katj.user.util.UserStatus
import jakarta.persistence.*
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate


@Entity(name = "users")
@DynamicInsert
@DynamicUpdate
class User (
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,

        @OneToMany(mappedBy = "user")
        var favorites: MutableList<Favorite>? = mutableListOf<Favorite>(),

        var name: String = "",
        var phoneNumber: String = "",
        var email: String = "",

        @Enumerated(EnumType.STRING)
        var gender: Gender? = null,

        @OneToMany(mappedBy = "user")
        var paymentMethods: MutableList<PaymentMethod>? = mutableListOf(),

        @Embedded
        var roadAddress: RoadAddress? = null,

        var status: UserStatus
): BaseEntity(){

        override fun equals(other: Any?): Boolean {
                if (this === other) return true
                if (javaClass != other?.javaClass) return false

                other as User

                if (id != other.id) return false
                if (favorites != other.favorites) return false
                if (name != other.name) return false
                if (phoneNumber != other.phoneNumber) return false
                if (email != other.email) return false
                if (gender != other.gender) return false
                if (paymentMethods != other.paymentMethods) return false
                if (roadAddress != other.roadAddress) return false
                if (status != other.status) return false

                return true
        }

        override fun hashCode(): Int {
                var result = id?.hashCode() ?: 0
                result = 31 * result + (favorites?.hashCode() ?: 0)
                result = 31 * result + name.hashCode()
                result = 31 * result + phoneNumber.hashCode()
                result = 31 * result + email.hashCode()
                result = 31 * result + (gender?.hashCode() ?: 0)
                result = 31 * result + (paymentMethods?.hashCode() ?: 0)
                result = 31 * result + (roadAddress?.hashCode() ?: 0)
                result = 31 * result + status.hashCode()
                return result
        }

        override fun toString(): String {
                return "User(id=$id, favorites=$favorites, name='$name', phoneNumber='$phoneNumber', email='$email', gender=$gender, paymentMethods=$paymentMethods, roadAddress=$roadAddress, status=$status)"
        }


}