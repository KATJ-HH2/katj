package com.hh2.katj.trip.model

import jakarta.persistence.Embeddable

@Embeddable
data class DepartureRoadAddress(
    val departureAddressName: String?,
    val departureRegion1depthName: String?,
    val departureRegion2depthName: String?,
    val departureRegion3depthName: String?,
    val departureRoadName: String?,
    val departureUndergroundYn: String?,
    val departureMainBuildingNo: String?,
    val departureSubBuildingNo: String?,
    val departureBuildingName: String?,
    val departureZoneNo: String?,
    val departureLongitude: String?,
    val departureLatitude: String?,
)