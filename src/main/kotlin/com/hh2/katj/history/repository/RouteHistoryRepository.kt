package com.hh2.katj.history.repository

import com.hh2.katj.history.model.entity.SearchRouteHistory
import org.springframework.data.jpa.repository.JpaRepository

interface RouteHistoryRepository : JpaRepository<SearchRouteHistory, Long> {
}