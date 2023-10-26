package com.hh2.katj.taxi.controller

import com.hh2.katj.taxi.model.dto.request.RequestAddTaxi
import com.hh2.katj.taxi.model.dto.request.toEntity
import com.hh2.katj.taxi.model.dto.response.ResponseAddTaxi
import com.hh2.katj.taxi.service.TaxiService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
class TaxiController(
    private val taxiService: TaxiService
) {
    @PostMapping("/taxi")
    fun addTaxi(@RequestBody requestAddTaxi: RequestAddTaxi): ResponseEntity<ResponseAddTaxi> {
        val taxiResponse = taxiService.addTaxi(requestAddTaxi.toEntity())
        return ResponseEntity.created(URI.create("/taxi/" + taxiResponse.id)).body(taxiResponse)
    }
}