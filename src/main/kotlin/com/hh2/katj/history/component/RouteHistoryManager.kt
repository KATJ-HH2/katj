package com.hh2.katj.history.component

import com.hh2.katj.history.model.entity.SearchRouteHistory
import com.hh2.katj.history.repository.RouteHistoryRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class RouteHistoryManager(
    private val routeHistoryRepository: RouteHistoryRepository,
) {

    @Transactional
    fun addRouteHistory(routeHistory: SearchRouteHistory): SearchRouteHistory {
        val history = routeHistoryRepository.save(routeHistory)
        return history
    }
}