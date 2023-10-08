package com.hh2.katj.util.model

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.persistence.Embeddable

@Embeddable
data class RoadAddress(
    @JsonProperty("address_name")
    val addressName: String?,

    @JsonProperty("region_1depth_name")
    val region1depthName: String?,

    @JsonProperty("region_2depth_name")
    val region2depthName: String?,

    @JsonProperty("region_3depth_name")
    val region3depthName: String?,

    @JsonProperty("road_name")
    val roadName: String?,

    @JsonProperty("underground_yn")
    val undergroundYn: String?,

    @JsonProperty("main_building_no")
    val mainBuildingNo: String?,

    @JsonProperty("sub_building_no")
    val subBuildingNo: String?,

    @JsonProperty("building_name")
    val buildingName: String?,

    @JsonProperty("zone_no")
    val zoneNo: String?,

    @JsonProperty("x")
    val longitude: String?,

    @JsonProperty("y")
    val latitude: String?,
)