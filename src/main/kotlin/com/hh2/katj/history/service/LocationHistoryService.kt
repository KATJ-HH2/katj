package com.hh2.katj.history.service

import com.hh2.katj.history.component.KakaoApiManager
import com.hh2.katj.history.component.LocationHistoryManager
import com.hh2.katj.history.model.dto.ResponseLocationHistory
import com.hh2.katj.user.model.entity.User
import com.hh2.katj.util.exception.failWithMessage
import org.springframework.stereotype.Service

@Service
class LocationHistoryService(
    private val locationHistoryManager: LocationHistoryManager,
    private val kakaoApiManager: KakaoApiManager,
) {

    fun saveLocationHistory(user: User, keyword: String): ResponseLocationHistory {
        val response = kakaoApiManager.requestAddressSearch(keyword)

        checkNotNull(response) {
            failWithMessage("")
        }

        val roadAddress = response.documents[0].roadAddress

        return locationHistoryManager.addLocationHistory(user, keyword, roadAddress).toResponseDto()
    }
}