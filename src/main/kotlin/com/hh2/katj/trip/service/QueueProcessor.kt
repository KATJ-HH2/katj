package com.hh2.katj.trip.service

import com.hh2.katj.taxidriver.model.TaxiDriver
import com.hh2.katj.taxidriver.model.TaxiDriverStatus
import com.hh2.katj.trip.controller.TripController
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.springframework.stereotype.Component
import java.util.concurrent.ConcurrentLinkedQueue

/**
 * Spring Bean 으로 등록되며 init 메서드로 자동 실행
 * 2023년 10월 14일 현재 사용하지 않음
 * 기능 구현 후 택시 기사 랜덤 배정 기능을 위해 사용할것
 */
@Component
class QueueProcessor (
    private val tripController: TripController,
){

    private val taxiDriverQueue: ConcurrentLinkedQueue<TaxiDriver> = ConcurrentLinkedQueue()

    init {
        GlobalScope.launch {
            scheduleQueueCheck()
        }
    }

    private suspend fun scheduleQueueCheck() {
        while (true) {
            // 3초마다 확인
            delay(3000)

            // queue에 데이터가 있는지 확인
            if (taxiDriverQueue.isNotEmpty()) {
                processQueueData()
            }
        }
    }

    private fun processQueueData() {
        while (taxiDriverQueue.isNotEmpty()) {
            val taxi = taxiDriverQueue.poll()
            /**
             * when 으로 나눠서 상태값에 따라 controller 호출
             */
            when (taxi.status) {
//                TripStatus.WAITING -> tripController.taxiAssignToUser()
//                TaxiDriverStatus.ENDDRIVE -> tripController.taxiDriverRequestPayToUser(taxi.user.id, taxi.id)
                else -> continue
            }

            /**
             * 완료 되지 않으면 계속 queue에서 사용
             */
            if (taxi.status != TaxiDriverStatus.ENDDRIVE) {
                taxiDriverQueue.offer(taxi)
            } else {
//                tripController.taxiDrivingFinish(trip.id)
            }
        }
    }

}