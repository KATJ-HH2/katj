package com.hh2.katj.taxi.model

import com.hh2.katj.util.model.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import org.springframework.cglib.core.Local
import java.time.LocalDate
import java.time.LocalDateTime
import javax.print.attribute.standard.PrinterMoreInfoManufacturer

@Entity
class Taxi(
    carNo: String,
    vin: String,
    kind: ChargeType,
    manufactureDate: LocalDate,
    fuel: FuelType,
    color: String,
    insureYN: Boolean,
    accidentYN: Boolean

    ): BaseEntity() {
    @Column(nullable = false)
    val carNo: String = carNo

    @Column(nullable = false)
    val vin: String = vin

    @Column(nullable = false)
    val kind: ChargeType = kind

    @Column(nullable = false)
    val manufactureDate: LocalDate = manufactureDate

    @Column(nullable = false)
    val fuel: FuelType = fuel

    @Column(nullable = false)
    val color: String = color

    @Column(nullable = false)
    val insureYN: Boolean = insureYN

    @Column(nullable = false)
    val accidentYN: Boolean = accidentYN
}