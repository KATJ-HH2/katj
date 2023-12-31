package com.hh2.katj.trip.service

import com.hh2.katj.payment.component.PaymentMethodReader
import com.hh2.katj.payment.model.entity.PaymentMethod
import com.hh2.katj.taxidriver.model.entity.TaxiDriver
import com.hh2.katj.trip.component.TripManager
import com.hh2.katj.trip.component.TripReader
import com.hh2.katj.trip.model.Trip
import com.hh2.katj.trip.model.TripStatus
import com.hh2.katj.trip.model.response.ResponseTrip
import com.hh2.katj.user.model.entity.User
import com.hh2.katj.user.service.UserService
import com.hh2.katj.util.exception.DataNotFoundException
import com.hh2.katj.util.exception.ExceptionMessage.INCORRECT_STATUS_VALUE
import org.springframework.stereotype.Service

@Service
class BillingService(
    private val tripReader: TripReader,
    private val tripManager: TripManager,
    private val userService: UserService,
    private val paymentMethodReader: PaymentMethodReader,
) {

    fun userPayWithRegiPaymentMethod(userId: Long, tripId: Long): ResponseTrip {
        val validatedUser = userValidation(userId)
        val validatedTrip = tripValidation(validatedUser.id, tripId)

        /**
         * 요청온 trip의 상태 코드가 ASSIGN_TAXI 인지 재검증
         */
        requestPayCheck(validatedTrip)

        /**
         * 택시 기사 상태가 WAITRING 인지 확인
         */
        taxiDriverStatusCheck(validatedTrip.taxiDriver!!)

        /**
         * 사용자 결제 수단 조회
         * 1.기본 결제 수단 존재시 먼저 사용
         * 2.기본 결제 수단 미존재시 리스트중 제일 빠른거 사용
         */
        val userPaymentMethod = selectPaymentMethod(validatedUser.id)

        /**
         * (구현 안했음)
         * 잔액 확인 후 모자라면 예외 발생
         * 잔액이 충분하면 결제 로직 작성
         */
        balanceCheck(userPaymentMethod, validatedTrip)

        /**
         * trip 상태 -> PAY_COMPLETE
         */
        val updateTrip = tripManager.updateTripStatusToPayComplete(validatedUser.id, tripId)

        /**
         * 사용자 입장에서 결제가 무사히 종료되면 기사에게 알리기?
         * 인데 그런거 없으니 PAY_COMPLETE로 변경되는거 기사쪽에서 알면 상태 변경 해야할듯
         */
        return updateTrip.toResponseDto()
    }

    private fun taxiDriverStatusCheck(taxiDriver: TaxiDriver) {
        taxiDriver.statusWaiting()
    }

    private fun requestPayCheck(trip: Trip) {
        if (trip.tripStatus != TripStatus.ASSIGN_TAXI) {
            throw IllegalArgumentException(INCORRECT_STATUS_VALUE.name)
        }
    }

    private fun balanceCheck(userPaymentMethod: PaymentMethod, trip: Trip) {
        /**
         * 외부 API 활용해서 결제수단 잔액 가져오고 trip의 fare랑 대조해보는 기능 추가적으로 필요 하지만..패스
         */
    }

    private fun tripValidation(userId: Long, tripId: Long): Trip {
        return tripReader.findByIdAndUserId(tripId, userId)
    }

    private fun selectPaymentMethod(userId: Long): PaymentMethod {
        var paymentMethod = paymentMethodReader.isDefaultExistCheck(userId)
        if (paymentMethod == null) {

            val paymentMethodList = paymentMethodReader.findAllPaymentMethod(userId)
            if (paymentMethodList.isEmpty()) {
                throw DataNotFoundException("no payment method exists")
            }

            paymentMethod = paymentMethodList[0]
        }
        return paymentMethod
    }


    private fun userValidation(userId: Long): User {
        val findUser = userService.findByUserId(userId)
        return userService.userValidationCheck(findUser.id)
    }


}