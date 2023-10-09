package com.hh2.katj.history.component

import com.hh2.katj.history.model.entity.SearchLocationHistory
import com.hh2.katj.history.repository.LocationHistoryRepository
import com.hh2.katj.user.model.entity.User
import com.hh2.katj.util.model.RoadAddress
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class LocationHistoryManager(
    private val locationHistoryRepository: LocationHistoryRepository,
) {

    @Transactional
    fun addLocationHistory(user: User, keyword: String, roadAddress: RoadAddress): SearchLocationHistory {
        val history = SearchLocationHistory(user = user, keyword = keyword, roadAddress = roadAddress)
        return locationHistoryRepository.save(history)
    }
}