package com.hh2.katj.domain.user.model.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.hh2.katj.util.model.RoadAddress

data class KakaoAddressSearchResponse(
    @JsonProperty("meta")
    var meta: Meta,

    @JsonProperty("documents")
    var documents: List<Document>,
)

data class Meta(
    @JsonProperty("total_count")
    val totalCount: Int? = 0,
)

data class Document(
    @JsonProperty("address_name")
    val addressName: String,

    @JsonProperty("address_type")
    val addressType: String,

    @JsonProperty("road_address")
    val roadAddress: RoadAddress,
)