package com.hh2.katj.history.model.dto

import jdk.jfr.Percentage

data class RequestLocationHistory(
    val userId: Long,
    val keyword: String,
    val faultPercentage: Int
)