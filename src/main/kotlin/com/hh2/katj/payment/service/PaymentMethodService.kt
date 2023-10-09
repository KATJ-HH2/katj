package com.hh2.katj.payment.service

import com.hh2.katj.payment.component.PaymentMethodManager
import com.hh2.katj.payment.component.PaymentMethodReader
import com.hh2.katj.payment.model.dto.response.ResponsePaymentMethod
import com.hh2.katj.payment.model.entity.Bank
import com.hh2.katj.payment.model.entity.PaymentMethod
import com.hh2.katj.user.component.UserManager
import com.hh2.katj.user.model.entity.User
import com.hh2.katj.user.service.UserService
import com.hh2.katj.util.exception.ExceptionMessage.*
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClientException

@Service
class PaymentMethodService (
    private val paymentMethodManager: PaymentMethodManager,
    private val paymentMethodReader: PaymentMethodReader,
    private val userService: UserService,
    private val userManager: UserManager,
    private val paymentMethodValidationApi: PaymentMethodValidationApi
){
    fun addBankAccount(userId: Long, request: PaymentMethod): ResponsePaymentMethod {
        val validatedUser = userService.userValidationCheck(userId)
        userService.userStatusActiveCheck(validatedUser)

        /**
         * 요청 계좌 인증 유/무 확인
         */
        if (!request.isValid!!) {
            throw IllegalArgumentException(INVALID_PAYMENT_METHOD.name)
        }

        duplicatedBankAccountCheck(userId, request)

        /**
         * 기본 결제 수단 요청시,
         * 사용자에 해당하는 다른 기본 결제 수단 존재시 해제 후 기본 결제 수단으로 지정
         * 사용자에 해당하는 다른 기본 결제 수단 미존재시 기본 결제 수단으로 지정
         */
        if (request.isDefault) {
            val isDefaultExistCheck = paymentMethodReader.isDefaultExistCheck(validatedUser.id)

            if (isDefaultExistCheck != null) {
                paymentMethodManager.changeDefaultToFalse(isDefaultExistCheck)
            }
        }

        /**
         * 사용자에게 결제 수단 추가
         */
        userManager.addPaymentMethodToUser(validatedUser, request)
        val addBankAccount = paymentMethodManager.addBankAccount(request)

        doubleDefaultCheck(validatedUser)

        return addBankAccount.toResponsePaymentMethod()
    }


    /**
     * 저장 요청 계좌의 인증 진행
     */
    fun validateBankAccount(request: PaymentMethod): Boolean {
        val duplicatedBankAccountCheck = Bank.isValidBankName(request.bankName)
        isValidBankName(duplicatedBankAccountCheck)

        val bankAccountValidationResult = paymentMethodValidationApi.bankAccountValidation(request)
        if (!bankAccountValidationResult){
            throw RestClientException(INVALID_PAYMENT_METHOD.name)
        }
        return bankAccountValidationResult
    }


    /**
     * 해당 사용자가 기등록한 계좌 정보인지 확인한다
     */
    private fun duplicatedBankAccountCheck(userId: Long, request: PaymentMethod) {
        val duplicatedBankAccountCheckResult = paymentMethodReader.duplicatedBankAccountCheck(userId, request)
        if (!duplicatedBankAccountCheckResult) {
            throw IllegalArgumentException(DUPLICATED_DATA_ALREADY_EXISTS.name)
        }
    }

    /**
     * 요청 은행이 BANK에 존재하는지 확인
     */
    private fun isValidBankName(bankNameCheck: Boolean) {
        if (!bankNameCheck) {
            throw IllegalArgumentException(NO_SUCH_VALUE_EXISTS.name)
        }
    }

    /**
     * 사용자가 기본 결제수단을 두개 이상 가지고 있는지 마지막 검증
     */
    internal fun doubleDefaultCheck(validatedUser: User) {
        val defaultList = paymentMethodReader.duplicatedDefaultPaymentMethodCheck(validatedUser.id)
        if (defaultList.size > 1) {
            throw IllegalStateException(DUPLICATED_DATA_ALREADY_EXISTS.name)
        }
    }

    fun findOnePaymentMethod(userId: Long, paymentMethodId: Long): ResponsePaymentMethod {
        val validatedUser = userService.userValidationCheck(userId)
        userService.userStatusActiveCheck(validatedUser)

        val findPaymentMethod = paymentMethodReader.findOnePaymentMethod(userId, paymentMethodId)

        return findPaymentMethod.toResponsePaymentMethod()
    }

    fun deleteOnePaymentMethod(userId: Long, paymentMethodId: Long): Boolean {
        val validatedUser = userService.userValidationCheck(userId)
        userService.userStatusActiveCheck(validatedUser)

        val deleteResult = paymentMethodManager.deleteOnePaymentMethod(validatedUser.id, paymentMethodId)
        return deleteResult
    }

    fun deleteAllPaymentMethod(userId: Long): Boolean {
        val validatedUser = userService.userValidationCheck(userId)
        userService.userStatusActiveCheck(validatedUser)

        val deleteAllResult = paymentMethodManager.deleteAllFavorite(validatedUser.id)
        return deleteAllResult
    }

    fun addCard(userId: Long, request: PaymentMethod): ResponsePaymentMethod {
        val validatedUser = userService.userValidationCheck(userId)
        userService.userStatusActiveCheck(validatedUser)

        /**
         * 요청 카드 인증 유/무 확인
         */
        if (!request.isValid!!) {
            throw IllegalArgumentException(INVALID_PAYMENT_METHOD.name)
        }

        duplicatedCardCheck(userId, request)

        /**
         * 기본 결제 수단 요청시,
         * 사용자에 해당하는 다른 기본 결제 수단 존재시 해제 후 기본 결제 수단으로 지정
         * 사용자에 해당하는 다른 기본 결제 수단 미존재시 기본 결제 수단으로 지정
         */
        if (request.isDefault) {
            val isDefaultExistCheck = paymentMethodReader.isDefaultExistCheck(validatedUser.id)

            if (isDefaultExistCheck != null) {
                paymentMethodManager.changeDefaultToFalse(isDefaultExistCheck)
            }
        }

        /**
         * 사용자에게 결제 수단 추가
         */
        userManager.addPaymentMethodToUser(validatedUser, request)
        val addBankAccount = paymentMethodManager.addBankAccount(request)

        doubleDefaultCheck(validatedUser)

        return addBankAccount.toResponsePaymentMethod()
    }

    /**
     * 해당 사용자가 기등록한 계좌 정보인지 확인한다
     */
    internal fun duplicatedCardCheck(userId: Long, request: PaymentMethod) {
        val duplicatedCardCheckResult = paymentMethodReader.duplicatedCardCheck(userId, request)
        if (!duplicatedCardCheckResult) {
            throw IllegalArgumentException(DUPLICATED_DATA_ALREADY_EXISTS.name)
        }
    }

}