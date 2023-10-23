package com.hh2.katj.user.model.request

import com.hh2.katj.user.model.entity.User
import com.hh2.katj.user.model.entity.UserStatus
import com.hh2.katj.util.model.Gender
import com.hh2.katj.util.model.RoadAddress

data class RequestCreateUser(
    var name: String,
    var phoneNumber: String,
    var email: String,
    var gender: Gender,
    var roadAddress: RoadAddress,
    var status: UserStatus,
) {
    fun toEntity(): User {
        return User(
            name = this.name,
            phoneNumber = this.phoneNumber,
            email = this.email,
            gender = this.gender,
            roadAddress = this.roadAddress,
            status = this.status
        )
    }
}
