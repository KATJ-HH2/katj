package com.hh2.katj.history.model.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.hh2.katj.util.model.RoadAddress

@JsonIgnoreProperties(ignoreUnknown = true)
data class NaverAddressSearchResponse(
    @JsonProperty("meta")
    var meta: NaverMeta,

    @JsonProperty("addresses")
    var addresses: List<Addresses>,
)

data class NaverMeta(
    @JsonProperty("totalCount")
    val totalCount: Int = 0,
)

data class AddressElements(
    @JsonProperty("types")
    val types: List<String>,

    @JsonProperty("longName")
    val zoneNo: String?
)

data class Addresses(
    @JsonProperty("roadAddress")
    val addressName: String?,

    @JsonProperty("addressElements")
    val addressElements: List<AddressElements>,

    @JsonProperty("x")
    val longitude: String?,

    @JsonProperty("y")
    val latitude: String?,
) {
    val roadAddress: RoadAddress by lazy {
        val separatedAddress: List<String> = addressName?.split(" ") ?: listOf()
        val zoneNo = addressElements.find { it.types.contains("POSTAL_CODE") }?.zoneNo ?: ""

        RoadAddress(
            addressName = addressName,
            region1depthName = separatedAddress.getOrNull(0) ?: "",
            region2depthName = separatedAddress.getOrNull(1) ?: "",
            region3depthName = separatedAddress.getOrNull(2) ?: "",
            roadName = separatedAddress.getOrNull(3) ?: "",
            undergroundYn = null,
            mainBuildingNo = separatedAddress.getOrNull(4) ?: "",
            subBuildingNo = null,
            buildingName = separatedAddress.getOrNull(5) ?: "",
            zoneNo = zoneNo,
            longitude = longitude,
            latitude = latitude,
        )
    }
}