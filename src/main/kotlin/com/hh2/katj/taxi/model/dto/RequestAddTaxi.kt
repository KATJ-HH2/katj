package com.hh2.katj.taxi.model.dto

import com.hh2.katj.taxi.model.ChargeType
import com.hh2.katj.taxi.model.FuelType
import com.hh2.katj.taxi.model.Taxi
import java.time.LocalDate

data class RequestAddTaxi(
    val carNo: String,
    val vin: String,
    val kind: ChargeType,
    val manufactureDate: LocalDate,
    val fuel: FuelType,
    val color: String,
    val insureYn: Boolean,
    val accidentYn: Boolean
)

fun RequestAddTaxi.toEntity() = Taxi(
    carNo = carNo,
    vin = vin,
    kind = kind,
    manufactureDate = manufactureDate,
    fuel = fuel,
    color = color,
    insureYN = insureYn,
    accidentYN = accidentYn
)
