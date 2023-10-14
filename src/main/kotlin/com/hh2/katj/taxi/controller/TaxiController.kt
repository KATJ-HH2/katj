package com.hh2.katj.taxi.controller

import com.hh2.katj.taxi.model.dto.RequestAddTaxi
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/taxi")
@RestController
class TaxiController {
    @PostMapping
    fun addTaxi(@RequestBody requestAddTaxi: RequestAddTaxi) {

    }
}