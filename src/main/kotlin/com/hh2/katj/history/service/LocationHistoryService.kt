package com.hh2.katj.history.service

import com.hh2.katj.config.RetryConfiguration
import com.hh2.katj.history.component.KakaoApiManager
import com.hh2.katj.history.component.LocationHistoryManager
import com.hh2.katj.history.component.NaverApiManager
import com.hh2.katj.history.model.dto.ResponseLocationHistory
import com.hh2.katj.user.model.entity.User
import com.hh2.katj.util.exception.ExceptionMessage
import com.hh2.katj.util.exception.failWithMessage
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import io.github.resilience4j.retry.annotation.Retry
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit

@Service
class LocationHistoryService(
    private val locationHistoryManager: LocationHistoryManager,
    private val kakaoApiManager: KakaoApiManager,
    private val naverApiManager: NaverApiManager,
    private val myCircuitBreaker: io.github.resilience4j.circuitbreaker.CircuitBreaker,
    @Autowired private val myRetry: io.github.resilience4j.retry.Retry
) {
    @CircuitBreaker(name="myCircuitBreaker")
    fun saveLocationHistory(user: User,
                            keyword: String): ResponseEntity<ResponseLocationHistory> {

        val addressSearchWithRetry = RetryConfiguration().decorateSupplier(myRetry) {
            kakaoApiManager.requestAddressSearch(keyword)
        }

        val response = addressSearchWithRetry.get()
        val responseBody = response?.body

        try {
            checkNotNull(responseBody) { "api 호출 오류" }
            check(responseBody.documents.isNotEmpty()) {
                failWithMessage(ExceptionMessage.NO_SEARCH_RESULT.name)
            }
        } catch(e: IllegalStateException) {
            myCircuitBreaker.onError(0, TimeUnit.SECONDS, RuntimeException())
            // OPEN 상태에서만 fallback 메서드를 직접 호출
            if (myCircuitBreaker.state == io.github.resilience4j.circuitbreaker.CircuitBreaker.State.OPEN) {
                return fallbackSaveLocationHistory(user, keyword, RuntimeException("CircuitBreaker is OPEN"))
            }
        }

        myCircuitBreaker.onSuccess(0, TimeUnit.SECONDS)
        val roadAddress = responseBody!!.documents[0].roadAddress

        return ResponseEntity.ok(locationHistoryManager.addLocationHistory(user, keyword, roadAddress).toResponseDto())
    }

    fun fallbackSaveLocationHistory(user: User,
                                    keyword: String,
                                    t: Throwable): ResponseEntity<ResponseLocationHistory> {

        println("fallback function is acting! -> " + t.message)
        val response = naverApiManager.requestAddressSearch(keyword)?.body
        checkNotNull(response) { "api 호출 오류" }
        check(response.addresses.isNotEmpty()) {
            failWithMessage(ExceptionMessage.NO_SEARCH_RESULT.name)
        }

        val roadAddress = response.addresses[0].address
        return ResponseEntity.ok(locationHistoryManager.addLocationHistory(user, keyword, roadAddress).toResponseDto())
    }
}