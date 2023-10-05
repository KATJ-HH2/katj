package com.hh2.katj.util.model

import jakarta.persistence.Embeddable

@Embeddable
class RoadAddress (
        private var addressName: String,
        private var region1depthName: String,
        private var region2depthName: String,
        private var region3depthName: String,
        private var roadName: String,
        private var undergroundYn: String,
        private var mainBuildingNo: String,
        private var subBuildingNo: String,
        private var buildingName: String,
        private var zoneNo: String,
        private var x: String,
        private var y: String
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

}