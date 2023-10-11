package com.hh2.katj.taxi.repository

import com.hh2.katj.taxi.model.Taxi
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TaxiRepository : JpaRepository<Taxi, Long> {
}