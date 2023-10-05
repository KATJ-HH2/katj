package com.hh2.katj.util.model

import jakarta.persistence.Embeddable

/**
 * 필드가 val로 정의되어 있기 때문에 역직렬화시 문제가 발생 할 수 있음
 * 나중에 필요시 수정할것
 */
@Embeddable
class RoadAddress (
        val addressName: String,
        val region1depthName: String,
        val region2depthName: String,
        val region3depthName: String,
        val roadName: String,
        val undergroundYn: String,
        val mainBuildingNo: String,
        val subBuildingNo: String,
        val buildingName: String,
        val zoneNo: String,
        val x: String,
        val y: String,
) {

    override fun toString(): String {
        return "RoadAddress(addressName='$addressName'" +
                ", region1depthName='$region1depthName'" +
                ", region2depthName='$region2depthName'" +
                ", region3depthName='$region3depthName'" +
                ", roadName='$roadName'" +
                ", undergroundYn='$undergroundYn'" +
                ", mainBuildingNo='$mainBuildingNo'" +
                ", subBuildingNo='$subBuildingNo'" +
                ", buildingName='$buildingName'" +
                ", zoneNo='$zoneNo'" +
                ", x='$x'" +
                ", y='$y')"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as RoadAddress

        if (addressName != other.addressName) return false
        if (region1depthName != other.region1depthName) return false
        if (region2depthName != other.region2depthName) return false
        if (region3depthName != other.region3depthName) return false
        if (roadName != other.roadName) return false
        if (undergroundYn != other.undergroundYn) return false
        if (mainBuildingNo != other.mainBuildingNo) return false
        if (subBuildingNo != other.subBuildingNo) return false
        if (buildingName != other.buildingName) return false
        if (zoneNo != other.zoneNo) return false
        if (x != other.x) return false
        if (y != other.y) return false

        return true
    }

    override fun hashCode(): Int {
        var result = addressName.hashCode()
        result = 31 * result + region1depthName.hashCode()
        result = 31 * result + region2depthName.hashCode()
        result = 31 * result + region3depthName.hashCode()
        result = 31 * result + roadName.hashCode()
        result = 31 * result + undergroundYn.hashCode()
        result = 31 * result + mainBuildingNo.hashCode()
        result = 31 * result + subBuildingNo.hashCode()
        result = 31 * result + buildingName.hashCode()
        result = 31 * result + zoneNo.hashCode()
        result = 31 * result + x.hashCode()
        result = 31 * result + y.hashCode()
        return result
    }

}