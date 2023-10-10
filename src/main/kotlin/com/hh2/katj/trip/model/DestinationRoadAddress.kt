package com.hh2.katj.trip.model

import jakarta.persistence.Embeddable

@Embeddable
data class DestinationRoadAddress(
    val destinationAddressName: String?,
    val destinationRegion1depthName: String?,
    val destinationRegion2depthName: String?,
    val destinationRegion3depthName: String?,
    val destinationRoadName: String?,
    val destinationUndergroundYn: String?,
    val destinationMainBuildingNo: String?,
    val destinationSubBuildingNo: String?,
    val destinationBuildingName: String?,
    val destinationZoneNo: String?,
    val destinationLongitude: String?,
    val destinationLatitude: String?,
)