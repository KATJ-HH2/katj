package com.hh2.katj.taxidriver.component

import com.hh2.katj.taxidriver.model.entity.TaxiDriver
import com.hh2.katj.taxidriver.model.entity.TaxiDriverStatus
import com.hh2.katj.taxidriver.repository.TaxiDriverRepository
import com.hh2.katj.util.exception.ExceptionMessage.USER_DOES_NOT_EXIST
import com.hh2.katj.util.exception.ExceptionMessage.INTERNAL_SERVER_ERROR_FROM_DATABASE
import com.hh2.katj.util.exception.findByIdOrThrowMessage
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class TaxiDriverManager(
    private val taxiDriverRepository: TaxiDriverRepository
) {
    /**
     * taxiDriverId가 존재하면 해당 ID로 taxiDriver 객체 반환
     * 존재하지 않으면 에러메세지 로깅
     **/
    @Transactional(readOnly = true)
    fun findByTaxiDriverId(taxiDriverId: Long): TaxiDriver =
        taxiDriverRepository.findByIdOrThrowMessage(taxiDriverId, USER_DOES_NOT_EXIST.name)

    /**
     * 기사님 정보 저장
     **/
    @Transactional
    fun saveTotalInfo(request: TaxiDriver): TaxiDriver {
        val infoAddedTaxiDriver = taxiDriverRepository.save(request)
        return infoAddedTaxiDriver
    }

    /**
     * 기사님 상태 조회
     **/
    @Transactional(readOnly = true)
    fun findStatus(taxiDriverId: Long): TaxiDriver {
        val taxiDriver = findByTaxiDriverId(taxiDriverId)
        return taxiDriver
    }

    /**
     * 기사님 상태 변경
     **/
    @Transactional
    fun updateStatus(taxiDriverId: Long, request: TaxiDriver): TaxiDriver {
        val TaxiDriver = findByTaxiDriverId(taxiDriverId)
        try {
            TaxiDriver.updateStatus(request.status)
        } catch (e: Exception) {
            throw Exception(INTERNAL_SERVER_ERROR_FROM_DATABASE.name)
        }
        return TaxiDriver
    }
}
