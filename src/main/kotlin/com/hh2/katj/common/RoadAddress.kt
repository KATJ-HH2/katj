package com.hh2.katj.util.model

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.persistence.Embeddable

@Embeddable
data class RoadAddress(
    @JsonProperty("addressName")
    val addressName: String?,

    @JsonProperty("region1depthName")
    val region1depthName: String?,

    @JsonProperty("region2depthName")
    val region2depthName: String?,

    @JsonProperty("region3depthName")
    val region3depthName: String?,

    @JsonProperty("roadName")
    val roadName: String?,

    @JsonProperty("undergroundYn")
    val undergroundYn: String?,

    @JsonProperty("mainBuildingNo")
    val mainBuildingNo: String?,

    @JsonProperty("subBuildingNo")
    val subBuildingNo: String?,

    @JsonProperty("buildingName")
    val buildingName: String?,

    @JsonProperty("zoneNo")
    val zoneNo: String?,

    @JsonProperty("x")
    val longitude: String?,

    @JsonProperty("y")
    val latitude: String?,
)