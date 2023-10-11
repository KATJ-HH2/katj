package com.hh2.katj.payment.controller

import com.hh2.katj.payment.model.dto.request.RequestAddBankAccount
import com.hh2.katj.payment.model.dto.request.RequestAddCard
import com.hh2.katj.payment.model.dto.response.ResponsePaymentMethod
import com.hh2.katj.payment.service.PaymentMethodService
import com.hh2.katj.user.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping("/payment-method")
@RestController
class PaymentMethodController (
    private val paymentMethodService: PaymentMethodService,
    private val userService: UserService,
){

    /**
     * 사용자가 해당 결제 수단 조회
     */
    @GetMapping("/{paymentMethodId}")
    fun findOnePaymentMethod(@RequestParam userId: Long, @PathVariable paymentMethodId: Long): ResponseEntity<ResponsePaymentMethod> {
        val findOnePaymentMethod = paymentMethodService.findOnePaymentMethod(userId, paymentMethodId)
        return ResponseEntity.ok(findOnePaymentMethod)
    }

    /**
     * 사용자가 모든 결제 수단 조회
     */
    @GetMapping
    fun findAllPaymentMethod(@RequestParam userId: Long): ResponseEntity<List<ResponsePaymentMethod>> {
        val findAllPaymentMethod = paymentMethodService.findAllPaymentMethod(userId)
        return ResponseEntity.ok(findAllPaymentMethod)
    }

    /**
     * 사용자가 은행 계좌를 결제 수단으로 등록
     */

    @PostMapping("/bank-account")
    fun addBankAccount(@RequestParam userId: Long, @RequestBody requestAddBankAccount: RequestAddBankAccount): ResponseEntity<ResponsePaymentMethod> {
        val findUser = userService.findByUserId(userId)
        val addBankAccount = paymentMethodService.addBankAccount(userId, requestAddBankAccount.toEntity(findUser))
        return ResponseEntity.ok(addBankAccount)
    }

    /**
     * 사용자가 카드를 결제 수단으로 등록
     */
    @PostMapping("/card")
    fun addCard(@RequestParam userId: Long, @RequestBody requestAddCard: RequestAddCard): ResponseEntity<ResponsePaymentMethod> {
        val findUser = userService.findByUserId(userId)
        val addCard = paymentMethodService.addCard(userId, requestAddCard.toEntity(findUser))
        return ResponseEntity.ok(addCard)
    }

    /**
     * 외부 API로 계좌 정보 유효성 인증
     */
    @PostMapping("/valid/bank-account")
    fun validateBankAccount(@RequestParam userId: Long, @RequestBody requestAddBankAccount: RequestAddBankAccount): ResponseEntity<Boolean> {
        val findUser = userService.findByUserId(userId)
        val validateResult = paymentMethodService.validateBankAccount(requestAddBankAccount.toEntity(findUser))
        return ResponseEntity.ok(validateResult)
    }

    /**
     * 외부 API로 카드 정보 유효성 인증
     */
    @PostMapping("/valid/card")
    fun validateCard(@RequestParam userId: Long, @RequestBody requestAddCard: RequestAddCard): ResponseEntity<Boolean> {
        val findUser = userService.findByUserId(userId)
        val validateResult = paymentMethodService.validateCard(requestAddCard.toEntity(findUser))
        return ResponseEntity.ok(validateResult)
    }

    /**
     * 사용자가 하나의 등록된 결제 수단을 삭제
     */
    @DeleteMapping("/{paymentMethodId}")
    fun deleteOnePaymentMethod(@RequestParam userId: Long, @PathVariable paymentMethodId: Long): ResponseEntity<Boolean> {
        val deleteResult = paymentMethodService.deleteOnePaymentMethod(userId, paymentMethodId)
        return ResponseEntity.ok(deleteResult)
    }

    /**
     * 사용자의 모든 등록된 결제 수단을 삭제
     */
    @DeleteMapping
    fun deleteAllPaymentMethod(@RequestParam userId: Long): ResponseEntity<Boolean> {
        val deleteResult = paymentMethodService.deleteAllPaymentMethod(userId)
        return ResponseEntity.ok(deleteResult)
    }

}