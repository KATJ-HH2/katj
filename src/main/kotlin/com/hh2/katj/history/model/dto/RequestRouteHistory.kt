package com.hh2.katj.history.model.dto

import com.hh2.katj.history.model.entity.SearchRouteHistory
import com.hh2.katj.trip.model.DepartureRoadAddress
import com.hh2.katj.trip.model.DestinationRoadAddress
import com.hh2.katj.user.model.entity.User

data class RequestRouteHistory(
    val userId: Long,
    val departureRoadAddress: DepartureRoadAddress,
    val destinationRoadAddress: DestinationRoadAddress,
) {
    fun toEntity(user: User): SearchRouteHistory {
        return SearchRouteHistory(
            user = user,
            departureRoadAddress = departureRoadAddress,
            destinationRoadAddress = destinationRoadAddress,
        )
    }
}