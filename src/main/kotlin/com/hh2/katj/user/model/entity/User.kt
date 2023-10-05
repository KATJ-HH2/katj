package com.hh2.katj.user.model.entity

import com.hh2.katj.util.model.BaseEntity
import com.hh2.katj.util.model.RoadAddress
import com.hh2.katj.favorite.model.Favorite
import com.hh2.katj.payment.model.entity.PaymentMethod
import jakarta.persistence.*
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import org.jetbrains.annotations.NotNull
import org.springframework.validation.annotation.Validated


@Entity(name = "users")
@DynamicInsert
@DynamicUpdate
class User (

        @Column(nullable = false)
        var name: String,

        @Column(nullable = false)
        var phoneNumber: String,

        @Column(nullable = false)
        var email: String,

        @Column(nullable = false)
        @Enumerated(EnumType.STRING)
        var gender: Gender,

        @OneToMany(mappedBy = "user")
        var paymentMethods: MutableList<PaymentMethod>? = mutableListOf(),

        @Embedded
        var roadAddress: RoadAddress?,

        var status: UserStatus?,

        @OneToMany(mappedBy = "user")
        var favorites: MutableList<Favorite>? = mutableListOf<Favorite>(),

        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null

): BaseEntity(){
}