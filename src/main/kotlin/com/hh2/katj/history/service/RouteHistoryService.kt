package com.hh2.katj.history.service

import com.hh2.katj.history.component.KakaoApiManager
import com.hh2.katj.history.component.RouteHistoryManager
import com.hh2.katj.history.model.dto.KakaoRouteSearchResponse
import com.hh2.katj.history.model.dto.Summary
import com.hh2.katj.history.model.entity.SearchRouteHistory
import com.hh2.katj.user.service.UserService
import com.hh2.katj.util.exception.ExceptionMessage
import com.hh2.katj.util.exception.failWithMessage
import org.springframework.stereotype.Service

@Service
class RouteHistoryService(
    private val kakaoApiManager: KakaoApiManager,
    private val routeHistoryManager: RouteHistoryManager,
    private val userService: UserService,
) {

    fun addRouteHistory(userId: Long, routeHistory: SearchRouteHistory): Summary {
        val validatedUser = userService.userValidationCheck(userId)
        userService.userStatusActiveCheck(validatedUser)

        val response = kakaoApiManager.requestCarDirectionSearch(
            routeHistory.departureRoadAddress,
            routeHistory.destinationRoadAddress
        )

        checkNotNull(response) { "api 호출 오류" }
        check(response.routes.isNotEmpty()) {
            failWithMessage(ExceptionMessage.NO_SEARCH_RESULT.name)
        }

        routeHistoryManager.addRouteHistory(routeHistory)

        return response.routes[0].summary
    }

}