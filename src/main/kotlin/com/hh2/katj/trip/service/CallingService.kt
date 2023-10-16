package com.hh2.katj.trip.service

import com.hh2.katj.payment.component.PaymentMethodReader
import com.hh2.katj.trip.component.TripManager
import com.hh2.katj.trip.component.TripReader
import com.hh2.katj.trip.model.Trip
import com.hh2.katj.trip.model.TripStatus
import com.hh2.katj.trip.model.response.ResponseTrip
import com.hh2.katj.user.model.entity.User
import com.hh2.katj.user.service.UserService
import com.hh2.katj.util.exception.ExceptionMessage.*
import com.hh2.katj.util.exception.failWithMessage
import org.springframework.stereotype.Service

@Service
class CallingService (
    private val tripReader: TripReader,
    private val tripManager: TripManager,
    private val userService: UserService,
    private val paymentMethodReader: PaymentMethodReader,
){

    fun callTaxiByUser(request: Trip): ResponseTrip {
        /**
         * 사용자 유효성 확인
         */
        val validatedUser = userValidation(request.user.id)

        /**
         * request 요청 상태가 CALLING 인지 확인
         */
        userCallTaxiCheck(request)

        /**
         * trip 테이블 추가
         */
        val savedTrip = tripManager.callTaxiByUser(request)

        /**
         * 정상 저장시 trip 테이블의 상태를 CALLED_BY_USER로 변경
         */
        savedTrip.updateStatus(TripStatus.CALL_TAXI)

        return savedTrip.toResponseDto()
    }

    private fun userCallTaxiCheck(request: Trip) {
        if (request.tripStatus != TripStatus.CALL_TAXI) {
            throw failWithMessage(INCORRECT_STATUS_VALUE.name)
        }
    }

    private fun userValidation(userId: Long): User {
        val findUser = userService.findByUserId(userId)
        return userService.userValidationCheck(findUser.id)
    }

    fun findOneEndTripByUser(userId: Long, tripId: Long): ResponseTrip {
        val validatedUser = userValidation(userId)

        val findOneTrip = tripReader.findOneEndTripByUser(validatedUser.id, tripId)
        return findOneTrip.toResponseDto()
    }

    fun findAllEndTripByUser(userId: Long): List<ResponseTrip> {
        val validatedUser = userValidation(userId)

        return tripReader.findAllEndTripByUserId(validatedUser.id).map(Trip::toResponseDto)
    }

}