package com.hh2.katj.taxi.model.dto.request

import com.hh2.katj.taxi.model.ChargeType
import com.hh2.katj.taxi.model.FuelType
import com.hh2.katj.taxi.model.Taxi
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.time.LocalDate

data class RequestAddTaxi(
    @NotBlank(message = "차량번호는 필수 입니다.")
    val carNo: String,

    @NotBlank(message = "차대번호는 필수 입니다.")
    val vin: String,

    @NotNull(message = "")
    val kind: ChargeType,

    @NotNull(message = "제조일자는 필수 입니다.")
    val manufactureDate: LocalDate,

    @NotNull(message = "연료 타입을 선택해주세요.")
    val fuel: FuelType,

    @NotBlank(message = "색상은 필수입니다.")
    val color: String,

    @NotNull(message = "보험여부 값은 필수입니다.")
    val insureYn: Boolean,

    @NotNull(message = "사고여부 값은 필수입니다.")
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
