package com.hh2.katj.trip.service

import com.hh2.katj.payment.service.PaymentMethodService
import com.hh2.katj.trip.component.TripManager
import com.hh2.katj.trip.component.TripReader
import com.hh2.katj.trip.model.response.ResponseTrip
import com.hh2.katj.user.model.entity.User
import com.hh2.katj.user.service.UserService
import org.springframework.stereotype.Service

@Service
class BillingService(
    private val tripReader: TripReader,
    private val tripManager: TripManager,
    private val userService: UserService,
    private val paymentMethodService: PaymentMethodService,
) {

    fun userPayWithRegiInfo(userId: Long, tripId: Long): ResponseTrip {
        val validatedUser = userValidation(userId)


        /**
         * 사용자 결제 수단 조회
         * 1.기본 결제 수단 존재시 먼저 사용
         * 2.기본 결제 수단 미존재시 리스트중 제일 빠른거 사용
         */



        /**
         * 사용자 결제 수단 외부 API로 유효성 검증
         * - 계좌 검증
         * - 카드 검증
         */

        /**
         * 잔액 확인 후 모자라면 예외 발생
         */

        /**
         * 잔액이 충분하면 결제 로직 작성
         */

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


    private fun userValidation(userId: Long): User {
        val findUser = userService.findByUserId(userId)
        return userService.userValidationCheck(findUser.id)
    }


}