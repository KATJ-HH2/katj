package com.hh2.katj.history.controller

import com.hh2.katj.history.model.dto.RequestLocationHistory
import com.hh2.katj.history.model.dto.ResponseLocationHistory
import com.hh2.katj.history.service.LocationHistoryService
import com.hh2.katj.user.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/location")
@RestController
class LocationHistoryController(
    private val locationHistoryService: LocationHistoryService,
    private val userService: UserService,
) {

    /**
     * 검색어로 위치정보(RoadAddress) 검색 및 이력 저장
     */
    @PostMapping
    fun addLocationHistory(@RequestBody request: RequestLocationHistory): ResponseEntity<ResponseLocationHistory> {
        val user = userService.findByUserId(userId = request.userId)
        val response = locationHistoryService.saveLocationHistory(user, request.keyword, request.faultPercentage)
        return ResponseEntity.ok(response)
    }

}