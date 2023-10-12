package com.hh2.katj.history.model.dto

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
    val totalCount: Int = 0,
)

data class Document(
    @JsonProperty("address_name")
    val addressName: String,

    @JsonProperty("address_type")
    val addressType: String,

    @JsonProperty("road_address")
    val roadAddressResponse: RoadAddressResponse,

    val roadAddress: RoadAddress = roadAddressResponse.toRoadAddress(),
)

data class RoadAddressResponse(
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
) {
    fun toRoadAddress(): RoadAddress {
        return RoadAddress(
            addressName = addressName,
            region1depthName = region1depthName,
            region2depthName = region2depthName,
            region3depthName = region3depthName,
            roadName = roadName,
            undergroundYn = undergroundYn,
            mainBuildingNo = mainBuildingNo,
            subBuildingNo = subBuildingNo,
            buildingName = buildingName,
            zoneNo = zoneNo,
            longitude = longitude,
            latitude = latitude,
        )
    }
}