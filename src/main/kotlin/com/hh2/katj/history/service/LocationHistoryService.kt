package com.hh2.katj.history.service

import com.hh2.katj.config.RetryConfiguration
import com.hh2.katj.history.component.KakaoApiManager
import com.hh2.katj.history.component.LocationHistoryManager
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
import kotlin.random.Random

@Service
class LocationHistoryService(
    private val locationHistoryManager: LocationHistoryManager,
    private val kakaoApiManager: KakaoApiManager,
    @Autowired private val myCircuitBreaker: io.github.resilience4j.circuitbreaker.CircuitBreaker,
    @Autowired private val myRetry: io.github.resilience4j.retry.Retry
) {
    @Retry(name="saveLocationHistory")
    @CircuitBreaker(name="saveLocationHistory")
    fun saveLocationHistory(user: User,
                            keyword: String,
                            faultPercentage: Int): ResponseEntity<ResponseLocationHistory> {

        val addressSearchWithRetry = RetryConfiguration().decorateSupplier(myRetry) {
            kakaoApiManager.requestAddressSearch(keyword)
        }
        val response = addressSearchWithRetry.get()
        val responseBody = response?.body
//        val random = Random.nextInt(0, 100)
//        if (faultPercentage > random) {
//            println(myCircuitBreaker.state)
//            myCircuitBreaker.onError(0, TimeUnit.SECONDS, RuntimeException())
//            // OPEN 상태에서만 fallback 메서드를 직접 호출
//            if (myCircuitBreaker.state == io.github.resilience4j.circuitbreaker.CircuitBreaker.State.OPEN) {
//                return fallbackSaveLocationHistory(user, keyword, faultPercentage, RuntimeException("CircuitBreaker is OPEN"))
//            }
//        }
        try {
            checkNotNull(responseBody) { "api 호출 오류" }
            check(responseBody.documents.isNotEmpty()) {
            failWithMessage(ExceptionMessage.NO_SEARCH_RESULT.name)
            }
        } catch(e: IllegalStateException) {
            myCircuitBreaker.onError(0, TimeUnit.SECONDS, RuntimeException())
            // OPEN 상태에서만 fallback 메서드를 직접 호출
            if (myCircuitBreaker.state == io.github.resilience4j.circuitbreaker.CircuitBreaker.State.OPEN) {
                return fallbackSaveLocationHistory(user, keyword, faultPercentage, RuntimeException("CircuitBreaker is OPEN"))
            }
        }

        myCircuitBreaker.onSuccess(0, TimeUnit.SECONDS)
        val roadAddress = responseBody!!.documents[0].roadAddress

        return ResponseEntity.ok(locationHistoryManager.addLocationHistory(user, keyword, roadAddress, faultPercentage).toResponseDto())
    }

    fun fallbackSaveLocationHistory(user: User,
                                    keyword: String,
                                    faultPercentage: Int,
                                    t: Throwable): ResponseEntity<ResponseLocationHistory> {

        println("fallback function is acting! -> " + t.message)
        val response = kakaoApiManager.requestAddressSearch(keyword)?.body
        checkNotNull(response) { "api 호출 오류" }
        check(response.documents.isNotEmpty()) {
            failWithMessage(ExceptionMessage.NO_SEARCH_RESULT.name)
        }

        val roadAddress = response.documents[0].roadAddress
        return ResponseEntity.ok(locationHistoryManager.addLocationHistory(user, keyword, roadAddress, faultPercentage).toResponseDto())
    }
}