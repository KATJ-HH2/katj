package com.hh2.katj.history.controller

import com.hh2.katj.history.model.dto.RequestRouteHistory
import com.hh2.katj.history.model.dto.Summary
import com.hh2.katj.history.service.RouteHistoryService
import com.hh2.katj.user.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/route")
@RestController
class RouteHistoryController(
    private val routeHistoryService: RouteHistoryService,
    private val userService: UserService,
) {

    /**
     * 출/도착 정보로 택시 요금 조회 및 이력 저장
     */
    @PostMapping
    fun addRouteHistory(
        @RequestBody requestRouteHistory: RequestRouteHistory,
    ): ResponseEntity<Summary> {
        val findUser = userService.findByUserId(requestRouteHistory.userId)
        val summary =
            routeHistoryService.addRouteHistory(
                findUser.id,
                requestRouteHistory.toEntity(findUser)
            )
        return ResponseEntity.ok(summary)
    }

}