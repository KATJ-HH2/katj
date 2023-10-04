package com.hh2.katj.favorite.model

import com.hh2.katj.util.model.RoadAddress

data class RequestFavorite(
        var roadAddress: RoadAddress,
        var title: String,
        var description: String?
)
