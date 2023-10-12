package com.hh2.katj.history.model.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class KakaoRouteSearchResponse(
    @JsonProperty("trans_id")
    val transId: String,
    @JsonProperty("routes")
    val routes: List<Routes>,
)

data class Routes(
    @JsonProperty("result_code")
    val resultCode: Int,
    @JsonProperty("result_msg")
    val resultMsg: String,
    @JsonProperty("summary")
    val summary: Summary,
)

data class Summary(
    @JsonProperty("fare")
    val fare: Fare,
    @JsonProperty("distance")
    val distance: Int,
    @JsonProperty("duration")
    val duration: Int,
)

data class Fare(
    @JsonProperty("taxi")
    val taxiFare: Int,
    @JsonProperty("toll")
    val tollFare: Int,
)
