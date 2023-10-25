package com.hh2.katj.taxi.model.dto.response

import com.hh2.katj.taxi.model.FuelType

data class FindTaxiResponse(
    val id: Long,
    val carNo: String,
    val fuelType: FuelType,
    val color: String
)
