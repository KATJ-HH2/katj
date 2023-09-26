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
}