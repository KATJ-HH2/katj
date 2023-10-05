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
}