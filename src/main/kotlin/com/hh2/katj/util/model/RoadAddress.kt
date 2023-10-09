package com.hh2.katj.util.model

import jakarta.persistence.Embeddable

@Embeddable
data class RoadAddress(
    val addressName: String?,
    val region1depthName: String?,
    val region2depthName: String?,
    val region3depthName: String?,
    val roadName: String?,
    val undergroundYn: String?,
    val mainBuildingNo: String?,
    val subBuildingNo: String?,
    val buildingName: String?,
    val zoneNo: String?,
    val longitude: String?,
    val latitude: String?,
)