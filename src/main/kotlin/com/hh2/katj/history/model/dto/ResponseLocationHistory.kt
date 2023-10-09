package com.hh2.katj.history.model.dto

import com.hh2.katj.util.model.RoadAddress

data class ResponseLocationHistory(
    val userId: Long,
    val roadAddress: RoadAddress,
)