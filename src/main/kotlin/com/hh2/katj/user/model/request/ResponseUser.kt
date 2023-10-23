package com.hh2.katj.user.model.request

import com.hh2.katj.user.model.entity.UserStatus
import com.hh2.katj.util.model.Gender
import com.hh2.katj.util.model.RoadAddress

data class ResponseUser(
    var id: Long?,
    var name: String,
    var phoneNumber: String,
    var email: String,
    var gender: Gender,
    var roadAddress: RoadAddress,
    var status: UserStatus,
)
