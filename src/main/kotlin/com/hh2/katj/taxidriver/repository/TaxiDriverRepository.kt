package com.hh2.katj.taxidriver.repository

import com.hh2.katj.taxidriver.model.entity.TaxiDriver
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TaxiDriverRepository : JpaRepository<TaxiDriver, Long> {

    fun findByStatus(status: TaxiDriverStatus): List<TaxiDriver>
}