package com.hh2.katj.favorite.model

import com.hh2.katj.cmn.model.RoadAddress
import com.hh2.katj.user.model.User
import jakarta.persistence.*

data class RequestFavorite(
        var roadAddress: RoadAddress,
        var title: String,
        var description: String?
)
