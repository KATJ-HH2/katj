package com.hh2.katj.history.repository

import com.hh2.katj.history.model.entity.SearchLocationHistory
import org.springframework.data.jpa.repository.JpaRepository

interface LocationHistoryRepository : JpaRepository<SearchLocationHistory, Long> {
}