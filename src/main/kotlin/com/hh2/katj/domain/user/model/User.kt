package com.hh2.katj.domain.user.model

import com.hh2.katj.common.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "users")
class User(
    userName: String,
    phoneNumber: String,
    email: String,
    gender: Gender,
) : BaseEntity() {

    @Column(nullable = false)
    var userName: String = userName
        protected set

    @Column(nullable = false)
    var phoneNumber: String = phoneNumber
        protected set

    @Column(nullable = false)
    var email: String = email
        protected set

    @Enumerated(EnumType.STRING)
    var gender: Gender = gender
        protected set
}