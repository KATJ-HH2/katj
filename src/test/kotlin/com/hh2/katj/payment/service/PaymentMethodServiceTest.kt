package com.hh2.katj.payment.service

import com.hh2.katj.payment.component.PaymentMethodReader
import com.hh2.katj.payment.model.dto.request.RequestAddBankAccount
import com.hh2.katj.payment.model.dto.request.RequestAddCard
import com.hh2.katj.payment.model.dto.response.ResponsePaymentMethod
import com.hh2.katj.payment.model.entity.Bank
import com.hh2.katj.payment.model.entity.PaymentMethod
import com.hh2.katj.payment.repository.PaymentMethodRepository
import com.hh2.katj.user.model.entity.User
import com.hh2.katj.user.model.entity.UserStatus
import com.hh2.katj.user.repository.UserRepository
import com.hh2.katj.util.annotation.KATJTestContainerE2E
import com.hh2.katj.util.exception.ExceptionMessage.*
import com.hh2.katj.util.model.BaseTestEnitity
import com.hh2.katj.util.model.Gender
import com.hh2.katj.util.model.RoadAddress
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.web.client.RestClientException
import java.time.LocalDateTime

@KATJTestContainerE2E
class PaymentMethodServiceTest(
    private val paymentMethodService: PaymentMethodService,
    private val paymentMethodRepository: PaymentMethodRepository,
    private val paymentMethodReader: PaymentMethodReader,
    private val userRepository: UserRepository,
): BaseTestEnitity(){

    @AfterEach
    fun tearDown() {
        paymentMethodRepository.deleteAllInBatch()
        userRepository.deleteAllInBatch()
    }

    val roadAddress: RoadAddress = RoadAddress(
        addressName = "address_name",
        region1depthName = "r_1",
        region2depthName = "r_2",
        region3depthName = "r_3",
        roadName = "road_name",
        undergroundYn = "Y",
        mainBuildingNo = "1",
        subBuildingNo = "2",
        buildingName = "bn",
        zoneNo = "11232",
        longitude = "x.123",
        latitude = "y.321",
    )

    @Test
    fun `사용자가 은행을 선택하여 계좌 결제 정보를 등록한다`() {
        // given
        val user = User(
            name = "newUser",
            phoneNumber = "123-456-789",
            email = "user@gmail.com",
            gender = Gender.MALE,
            status = UserStatus.ACTIVE,
            roadAddress = roadAddress
        )

        val bankAccountInfo = RequestAddBankAccount(
            isDefault = true,
            bankAccountNumber = "111-111-111111",
            bankName = Bank.SHINHAN,
            isValid = true,
        )

        // when
        val saveUser = userRepository.save(user)
        val addBankAccount = paymentMethodService.addBankAccount(saveUser.id, bankAccountInfo.toEntity(saveUser))

        // then
        assertThat(bankAccountInfo.isDefault).isEqualTo(addBankAccount.isDefault)
        assertThat(bankAccountInfo.bankAccountNumber).isEqualTo(addBankAccount.bankAccountNumber)
        assertThat(bankAccountInfo.bankName).isEqualTo(addBankAccount.bankName)
        assertThat(addBankAccount.isValid).isTrue()
        assertThat(saveUser).isEqualTo(addBankAccount.user)
    }

    @Test
    fun `사용자가 카드를 결제 정보로 등록한다`() {
        // given
        val user = User(
            name = "newUser",
            phoneNumber = "123-456-789",
            email = "user@gmail.com",
            gender = Gender.MALE,
            status = UserStatus.ACTIVE,
            roadAddress = roadAddress
        )

        val cardInfo = RequestAddCard(
            isDefault = true,
            isValid = true,
            cardHolderName = "KATJ LEE",
            cardNumber = "1111-1111-1111-1111",
            expiryDate = LocalDateTime.now().plusDays(1),
            cvv = "777"
        )

        // when
        val saveUser = userRepository.save(user)
        val cardPaymentMethod = cardInfo.toEntity(saveUser)
        paymentMethodService.validateCard(cardPaymentMethod)

        val addCard = paymentMethodService.addCard(saveUser.id, cardPaymentMethod)

        // then
        assertThat(cardInfo.isDefault).isEqualTo(addCard.isDefault)
        assertThat(cardInfo.isValid).isEqualTo(addCard.isValid)
        assertThat(cardInfo.cardHolderName).isEqualTo(addCard.cardHolderName)
        assertThat(cardInfo.expiryDate).isEqualTo(addCard.expiryDate)
        assertThat(cardInfo.cvv).isEqualTo(addCard.cvv)
        assertThat(saveUser).isEqualTo(addCard.user)
    }

    @Test
    fun `사용자가 기등록한 계좌 정보면 등록 할 수 없다`() {
        // given
        val user = User(
            name = "newUser",
            phoneNumber = "123-456-789",
            email = "user@gmail.com",
            gender = Gender.MALE,
            status = UserStatus.ACTIVE,
            roadAddress = roadAddress
        )

        val bankAccountInfo = RequestAddBankAccount(
            isDefault = true,
            bankAccountNumber = "111-111-111111",
            bankName = Bank.SHINHAN,
            isValid = true,
        )

        val saveUser = userRepository.save(user)
        val addBankAccount = paymentMethodService.addBankAccount(saveUser.id, bankAccountInfo.toEntity(saveUser))

        val duplicatedBankAccountInfo = RequestAddBankAccount(
            isDefault = true,
            bankAccountNumber = "111-111-111111",
            bankName = Bank.SHINHAN,
            isValid = true,
        )

        // when // then
        assertThrows<IllegalArgumentException> {
            paymentMethodService.addBankAccount(saveUser.id, duplicatedBankAccountInfo.toEntity(saveUser))
        }.apply {
            assertThat(message).isEqualTo(DUPLICATED_DATA_ALREADY_EXISTS.name)
        }
    }

    @Test
    fun `사용자가 기등록한 카드 정보면 등록 할 수 없다`() {
        // given
        val user = User(
            name = "newUser",
            phoneNumber = "123-456-789",
            email = "user@gmail.com",
            gender = Gender.MALE,
            status = UserStatus.ACTIVE,
            roadAddress = roadAddress
        )

        val cardInfo = RequestAddCard(
            isDefault = true,
            isValid = true,
            cardHolderName = "KATJ LEE",
            cardNumber = "1111-1111-1111-1111",
            expiryDate = LocalDateTime.now().plusDays(1),
            cvv = "777"
        )

        val saveUser = userRepository.save(user)
        paymentMethodService.addCard(saveUser.id, cardInfo.toEntity(saveUser))

        // when
        val duplicatedCardInfo = RequestAddCard(
            isDefault = true,
            isValid = true,
            cardHolderName = "KATJ LEE",
            cardNumber = "1111-1111-1111-1111",
            expiryDate = LocalDateTime.now().plusDays(1),
            cvv = "777"
        )

        // when // then
        assertThrows<IllegalArgumentException> {
            paymentMethodService.duplicatedCardCheck(saveUser.id, duplicatedCardInfo.toEntity(saveUser))
        }.apply {
            assertThat(message).isEqualTo(DUPLICATED_DATA_ALREADY_EXISTS.name)
        }
    }

    @Test
    fun `카드 정보 인증에 성공한다`() {
        // given
        val user = User(
            name = "newUser",
            phoneNumber = "123-456-789",
            email = "user@gmail.com",
            gender = Gender.MALE,
            status = UserStatus.ACTIVE,
            roadAddress = roadAddress,
        )

        val cardInfo = RequestAddCard(
            isDefault = true,
            isValid = null,
            cardHolderName = "KATJ LEE",
            cardNumber = "1111-1111-1111-1111",
            expiryDate = LocalDateTime.now().plusDays(1),
            cvv = "777",
        )

        val saveUser = userRepository.save(user)

        // when
        val cardValidationCheckResult = paymentMethodService.validateCard(cardInfo.toEntity(saveUser))

        // then
        assertThat(cardValidationCheckResult).isTrue()
    }

    @Disabled
    @Test
    fun `카드 정보 인증에 실패한다`() {
        // given
        val user = User(
            name = "newUser",
            phoneNumber = "123-456-789",
            email = "user@gmail.com",
            gender = Gender.MALE,
            status = UserStatus.ACTIVE,
            roadAddress = roadAddress,
        )

        val cardInfo = RequestAddCard(
            isDefault = true,
            isValid = null,
            cardHolderName = "KATJ LEE",
            cardNumber = "1111-1111-1111-1111",
            expiryDate = LocalDateTime.now().plusDays(1),
            cvv = "777",
        )

        val saveUser = userRepository.save(user)

        // when // then
        assertThrows<RestClientException> {
            paymentMethodService.validateCard(cardInfo.toEntity(saveUser))
        }.apply {
            assertThat(message).isEqualTo(INVALID_PAYMENT_METHOD.name)
        }
    }


    @Test
    fun `사용자가 등록하려는 카드의 유효기간이 지금보다 적게 남았다`() {
        // given
        val user = User(
            name = "newUser",
            phoneNumber = "123-456-789",
            email = "user@gmail.com",
            gender = Gender.MALE,
            status = UserStatus.ACTIVE,
            roadAddress = roadAddress,
        )

        val cardInfo = RequestAddCard(
            isDefault = true,
            isValid = true,
            cardHolderName = "KATJ LEE",
            cardNumber = "1111-1111-1111-1111",
            expiryDate = LocalDateTime.now().minusDays(1),
            cvv = "777",
        )

        val saveUser = userRepository.save(user)

        // when // then
        assertThrows<IllegalArgumentException> {
            paymentMethodService.cardExpiryDateCheck(cardInfo.toEntity(saveUser).expiryDate!!)
        }.apply {
            assertThat(message).isEqualTo(INVALID_PAYMENT_METHOD.name)
        }
    }

    @Test
    fun `사용자가 등록하려는 카드의 유효기간이 지금보다 많이 남았다`() {
        // given
        val user = User(
            name = "newUser",
            phoneNumber = "123-456-789",
            email = "user@gmail.com",
            gender = Gender.MALE,
            status = UserStatus.ACTIVE,
            roadAddress = roadAddress,
        )

        val cardInfo = RequestAddCard(
            isDefault = true,
            isValid = true,
            cardHolderName = "KATJ LEE",
            cardNumber = "1111-1111-1111-1111",
            expiryDate = LocalDateTime.now().plusDays(1),
            cvv = "777",
        )

        val saveUser = userRepository.save(user)

        // when // then
        val cardExpiryDateCheckResult = paymentMethodService.cardExpiryDateCheck(cardInfo.toEntity(saveUser).expiryDate!!)
        assertThat(cardExpiryDateCheckResult).isTrue()
    }

    @Test
    fun `(계좌, 계좌)사용자가 기존 기본 결제 정보가 있지만, 새로 기본 결제 정보를 저장 하려고 하면, 새로 등록되는 계좌가 기본 결제 정보로 등록된다`() {
        // given
        val user = User(
            name = "newUser",
            phoneNumber = "123-456-789",
            email = "user@gmail.com",
            gender = Gender.MALE,
            status = UserStatus.ACTIVE,
            roadAddress = roadAddress
        )

        val bankAccountInfo = RequestAddBankAccount(
            isDefault = true,
            bankAccountNumber = "121-121-121212",
            bankName = Bank.SHINHAN,
            isValid = true,
        )

        val saveUser = userRepository.save(user)
        val firstBankAccount = paymentMethodService.addBankAccount(saveUser.id, bankAccountInfo.toEntity(saveUser))

        // when
        val defaultTrueBankAccountInfo = RequestAddBankAccount(
            isDefault = true,
            bankAccountNumber = "111-111-111111",
            bankName = Bank.SHINHAN,
            isValid = true,
        )
        val secondBankAccount = paymentMethodService.addBankAccount(saveUser.id, defaultTrueBankAccountInfo.toEntity(saveUser))

        // then
        val findFirstBankAccount = paymentMethodRepository.findByUserIdAndBankNameAndBankAccountNumber(firstBankAccount.user!!.id, firstBankAccount.bankName, firstBankAccount.bankAccountNumber)
        val findSecondBankAccount = paymentMethodRepository.findByUserIdAndBankNameAndBankAccountNumber(secondBankAccount.user!!.id, secondBankAccount.bankName, secondBankAccount.bankAccountNumber)

        assertThat(findFirstBankAccount!!.isDefault).isFalse()
        assertThat(findSecondBankAccount!!.isDefault).isTrue()
    }

    @Test
    fun `(카드, 계좌)사용자가 기존 기본 결제 정보가 있지만, 새로 기본 결제 정보를 저장 하려고 하면, 새로 등록되는 계좌가 기본 결제 정보로 등록된다`() {
        // given
        val user = User(
            name = "newUser",
            phoneNumber = "123-456-789",
            email = "user@gmail.com",
            gender = Gender.MALE,
            status = UserStatus.ACTIVE,
            roadAddress = roadAddress
        )

        val bankAccountInfo = RequestAddBankAccount(
            isDefault = true,
            bankAccountNumber = "121-121-121212",
            bankName = Bank.SHINHAN,
            isValid = true,
        )

        val saveUser = userRepository.save(user)
        val defaultBankAccount = paymentMethodService.addBankAccount(saveUser.id, bankAccountInfo.toEntity(saveUser))

        // when
        val cardInfo = RequestAddCard(
            isDefault = true,
            isValid = true,
            cardHolderName = "KATJ LEE",
            cardNumber = "1111-1111-1111-1111",
            expiryDate = LocalDateTime.now().plusDays(1),
            cvv = "777"
        )

        val defaultCard = paymentMethodService.addCard(saveUser.id, cardInfo.toEntity(saveUser))

        // then
        val findDefaultBankAccount = paymentMethodRepository.findByUserIdAndBankNameAndBankAccountNumber(defaultBankAccount.user!!.id, defaultBankAccount.bankName, defaultBankAccount.bankAccountNumber)
        val findDefaultCard = paymentMethodRepository.findByUserIdAndCardNumberAndCardHolderName(defaultCard.user!!.id, defaultCard.cardNumber, defaultCard.cardHolderName)

        assertThat(findDefaultBankAccount!!.isDefault).isFalse()
        assertThat(findDefaultCard!!.isDefault).isTrue()
    }

    @Test
    fun `(카드, 카드)사용자가 기존 기본 결제 정보가 있지만, 새로 기본 결제 정보를 저장 하려고 하면, 새로 등록되는 계좌가 기본 결제 정보로 등록된다`() {
        // given
        val user = User(
            name = "newUser",
            phoneNumber = "123-456-789",
            email = "user@gmail.com",
            gender = Gender.MALE,
            status = UserStatus.ACTIVE,
            roadAddress = roadAddress
        )

        val oldCardInfo = RequestAddCard(
            isDefault = true,
            isValid = true,
            cardHolderName = "KATJ LEE",
            cardNumber = "1111-1111-1111-1111",
            expiryDate = LocalDateTime.now().plusDays(1),
            cvv = "777"
        )

        val saveUser = userRepository.save(user)
        val oldDefaultCard = paymentMethodService.addCard(saveUser.id, oldCardInfo.toEntity(saveUser))

        // when
        val newCardInfo = RequestAddCard(
            isDefault = true,
            isValid = true,
            cardHolderName = "KATJ LEE",
            cardNumber = "1212-1212-1212-1212",
            expiryDate = LocalDateTime.now().plusDays(1),
            cvv = "777"
        )

        val newDefaultCard = paymentMethodService.addCard(saveUser.id, newCardInfo.toEntity(saveUser))

        // then
        val findOldCard = paymentMethodRepository.findByUserIdAndCardNumberAndCardHolderName(oldDefaultCard.user!!.id, oldDefaultCard.cardNumber, oldDefaultCard.cardHolderName)
        val findNewCard = paymentMethodRepository.findByUserIdAndCardNumberAndCardHolderName(newDefaultCard.user!!.id, newDefaultCard.cardNumber, newDefaultCard.cardHolderName)

        assertThat(findOldCard!!.isDefault).isFalse()
        assertThat(findNewCard!!.isDefault).isTrue()
    }



    @Test
    fun `사용자가 새로 기본 결제 정보를 저장하려는데, 기존 기본 정보가 그대로 남아있다면 저장할 수 없다`() {
        // given
        val user = User(
            name = "newUser",
            phoneNumber = "123-456-789",
            email = "user@gmail.com",
            gender = Gender.MALE,
            status = UserStatus.ACTIVE,
            roadAddress = roadAddress
        )

        val bankAccountInfo = RequestAddBankAccount(
            isDefault = true,
            bankAccountNumber = "121-121-121212",
            bankName = Bank.SHINHAN,
            isValid = true,
        )

        val saveUser = userRepository.save(user)
        paymentMethodRepository.save(bankAccountInfo.toEntity(saveUser))

        // when
        val defaultTrueBankAccountInfo = RequestAddBankAccount(
            isDefault = true,
            bankAccountNumber = "111-111-111111",
            bankName = Bank.SHINHAN,
            isValid = true,
        )
        paymentMethodRepository.save(defaultTrueBankAccountInfo.toEntity(saveUser))

        // then
        assertThrows<IllegalStateException> {
            paymentMethodService.doubleDefaultCheck(saveUser)
        }.apply {
            assertThat(message).isEqualTo(DUPLICATED_DATA_ALREADY_EXISTS.name)
        }
    }

    @Test
    fun `계좌 정보 인증에 성공한다`() {
        // given
        val user = User(
            name = "newUser",
            phoneNumber = "123-456-789",
            email = "user@gmail.com",
            gender = Gender.MALE,
            status = UserStatus.ACTIVE,
            roadAddress = roadAddress
        )

        val bankAccountInfo = RequestAddBankAccount(
            isDefault = true,
            bankAccountNumber = "111-111-111111",
            bankName = Bank.SHINHAN,
            isValid = null,
        )

        // when
        val saveUser = userRepository.save(user)

        // then
        val validationResult = paymentMethodService.validateBankAccount(bankAccountInfo.toEntity(saveUser))
        assertThat(validationResult).isTrue()
    }

    @Test
    fun `인증되지 않은 계좌는 등록할 수 없다`() {
        // given
        val user = User(
            name = "newUser",
            phoneNumber = "123-456-789",
            email = "user@gmail.com",
            gender = Gender.MALE,
            status = UserStatus.ACTIVE,
            roadAddress = roadAddress
        )

        val bankAccountInfo = RequestAddBankAccount(
            isDefault = true,
            bankAccountNumber = "111-111-111111",
            bankName = Bank.SHINHAN,
            isValid = false,
        )

        // when
        val saveUser = userRepository.save(user)

        // then
        assertThrows<IllegalArgumentException> {
            paymentMethodService.addBankAccount(saveUser.id, bankAccountInfo.toEntity(saveUser))
        }.apply {
            assertThat(message).isEqualTo(INVALID_PAYMENT_METHOD.name)
        }
    }

    @Test
    fun `인증되지 않은 카드는 등록할 수 없다`() {
        // given
        val user = User(
            name = "newUser",
            phoneNumber = "123-456-789",
            email = "user@gmail.com",
            gender = Gender.MALE,
            status = UserStatus.ACTIVE,
            roadAddress = roadAddress
        )

        val cardInfo = RequestAddCard(
            isDefault = true,
            isValid = false,
            cardHolderName = "KATJ LEE",
            cardNumber = "1111-1111-1111-1111",
            expiryDate = LocalDateTime.now().plusDays(1),
            cvv = "777"
        )

        // when
        val saveUser = userRepository.save(user)

        // then
        assertThrows<IllegalArgumentException> {
            paymentMethodService.addBankAccount(saveUser.id, cardInfo.toEntity(saveUser))
        }.apply {
            assertThat(message).isEqualTo(INVALID_PAYMENT_METHOD.name)
        }
    }

    /**
     * 계좌 인증 API가 구현되어 있지 않아 예외를 발생시키는 조건이 성립하지 않음
     * 2023.10.09 tony
     */
    @Disabled
    @Test
    fun `계좌 정보 인증에 실패한다`() {
        // given
        val user = User(
            name = "newUser",
            phoneNumber = "123-456-789",
            email = "user@gmail.com",
            gender = Gender.MALE,
            status = UserStatus.ACTIVE,
            roadAddress = roadAddress
        )

        val bankAccountInfo = RequestAddBankAccount(
            isDefault = true,
            bankAccountNumber = "111-111-111111",
            bankName = Bank.SHINHAN,
            isValid = null,
        )

        // when
        val saveUser = userRepository.save(user)

        // then
        assertThrows<RestClientException> {
            paymentMethodService.validateBankAccount(bankAccountInfo.toEntity(saveUser))
        }.apply {
            assertThat(message).isEqualTo(INVALID_PAYMENT_METHOD.name)
        }
    }

    @Test
    fun `사용자가 등록한 계좌 정보를 조회한다`() {
        // given
        val user = User(
            name = "newUser",
            phoneNumber = "123-456-789",
            email = "user@gmail.com",
            gender = Gender.MALE,
            status = UserStatus.ACTIVE,
            roadAddress = roadAddress
        )

        val bankAccountInfo = RequestAddBankAccount(
            isDefault = true,
            bankAccountNumber = "111-111-111111",
            bankName = Bank.SHINHAN,
            isValid = true,
        )

        val saveUser = userRepository.save(user)
        val saveBankAccount = paymentMethodService.addBankAccount(saveUser.id, bankAccountInfo.toEntity(saveUser))

        // when
        val findPaymentMethod = paymentMethodService.findOnePaymentMethod(saveUser.id, saveBankAccount.id)

        // then
        assertThat(bankAccountInfo.bankAccountNumber).isEqualTo(findPaymentMethod.bankAccountNumber)
        assertThat(bankAccountInfo.bankName).isEqualTo(findPaymentMethod.bankName)
        assertThat(findPaymentMethod.isValid).isTrue()
        assertThat(findPaymentMethod.isDefault).isTrue()
        assertThat(saveUser).isEqualTo(findPaymentMethod.user)
        assertThat(findPaymentMethod.cardNumber).isNull()
        assertThat(findPaymentMethod.cardHolderName).isNull()
        assertThat(findPaymentMethod.cvv).isNull()
        assertThat(findPaymentMethod.expiryDate).isNull()
    }


    @Test
    fun `사용자가 등록한 카드 정보를 조회한다`() {
        // given
        val user = User(
            name = "newUser",
            phoneNumber = "123-456-789",
            email = "user@gmail.com",
            gender = Gender.MALE,
            status = UserStatus.ACTIVE,
            roadAddress = roadAddress
        )

        val cardInfo = RequestAddCard(
            isDefault = false,
            isValid = true,
            cardHolderName = "KATJ LEE",
            cardNumber = "1111-1111-1111-1111",
            expiryDate = LocalDateTime.now().plusDays(1),
            cvv = "777"
        )

        val saveUser = userRepository.save(user)
        val saveCard = paymentMethodService.addCard(saveUser.id, cardInfo.toEntity(saveUser))

        // when
        val findPaymentMethod = paymentMethodService.findOnePaymentMethod(saveUser.id, saveCard.id)

        // then
        assertThat(cardInfo.cardNumber).isEqualTo(findPaymentMethod.cardNumber)
        assertThat(cardInfo.cardHolderName).isEqualTo(findPaymentMethod.cardHolderName)
        assertThat(cardInfo.cvv).isEqualTo(findPaymentMethod.cvv)
        assertThat(cardInfo.expiryDate).isEqualTo(findPaymentMethod.expiryDate)
        assertThat(saveUser).isEqualTo(findPaymentMethod.user)
        assertThat(findPaymentMethod.isValid).isTrue()
        assertThat(findPaymentMethod.isDefault).isFalse()
        assertThat(findPaymentMethod.bankAccountNumber).isNull()
        assertThat(findPaymentMethod.bankName).isNull()
    }

    @Test
    fun `사용자가 등록한 계좌 정보를 삭제한다`() {
        // given
        val user = User(
            name = "newUser",
            phoneNumber = "123-456-789",
            email = "user@gmail.com",
            gender = Gender.MALE,
            status = UserStatus.ACTIVE,
            roadAddress = roadAddress
        )

        val bankAccountInfo = RequestAddBankAccount(
            isDefault = true,
            bankAccountNumber = "111-111-111111",
            bankName = Bank.SHINHAN,
            isValid = true,
        )

        val saveUser = userRepository.save(user)
        val saveBankAccount = paymentMethodService.addBankAccount(saveUser.id, bankAccountInfo.toEntity(saveUser))

        // when
        val deleteResult = paymentMethodService.deleteOnePaymentMethod(saveUser.id, saveBankAccount.id)

        // then
        assertThat(deleteResult).isTrue()
        assertThrows<IllegalArgumentException> {
            paymentMethodService.findOnePaymentMethod(saveUser.id, saveBankAccount.id)
        }.apply {
            assertThat(message).isEqualTo(ID_DOES_NOT_EXIST.name)
        }
    }

    @Test
    fun `사용자가 등록한 카드 정보를 삭제한다`() {
        // given
        val user = User(
            name = "newUser",
            phoneNumber = "123-456-789",
            email = "user@gmail.com",
            gender = Gender.MALE,
            status = UserStatus.ACTIVE,
            roadAddress = roadAddress
        )

        val cardInfo = RequestAddCard(
            isDefault = false,
            isValid = true,
            cardHolderName = "KATJ LEE",
            cardNumber = "1111-1111-1111-1111",
            expiryDate = LocalDateTime.now().plusDays(1),
            cvv = "777"
        )

        val saveUser = userRepository.save(user)
        val saveCard = paymentMethodService.addCard(saveUser.id, cardInfo.toEntity(saveUser))

        // when
        val deleteResult = paymentMethodService.deleteOnePaymentMethod(saveUser.id, saveCard.id)

        // then
        assertThat(deleteResult).isTrue()
        assertThrows<IllegalArgumentException> {
            paymentMethodService.findOnePaymentMethod(saveUser.id, saveCard.id)
        }.apply {
            assertThat(message).isEqualTo(ID_DOES_NOT_EXIST.name)
        }
    }

    @Test
    fun `사용자가 등록한 결제 수단을 모두 삭제한다`() {
        // given
        val user = User(
            name = "newUser",
            phoneNumber = "123-456-789",
            email = "user@gmail.com",
            gender = Gender.MALE,
            status = UserStatus.ACTIVE,
            roadAddress = roadAddress
        )

        val firstBankAccountInfo = RequestAddBankAccount(
            isDefault = true,
            bankAccountNumber = "111-111-111111",
            bankName = Bank.SHINHAN,
            isValid = true,
        )

        val secondBankAccountInfo = RequestAddBankAccount(
            isDefault = true,
            bankAccountNumber = "121-111-111111",
            bankName = Bank.SHINHAN,
            isValid = true,
        )

        val thirdCardInfo = RequestAddCard(
            isDefault = true,
            isValid = true,
            cardHolderName = "KATJ LEE",
            cardNumber = "1212-1212-1212-1212",
            expiryDate = LocalDateTime.now().plusDays(1),
            cvv = "878"
        )

        val fourthCardInfo = RequestAddCard(
            isDefault = true,
            isValid = true,
            cardHolderName = "KATJ LEE",
            cardNumber = "1111-1111-1111-1111",
            expiryDate = LocalDateTime.now().plusDays(1),
            cvv = "787"
        )


        val saveUser = userRepository.save(user)
        paymentMethodService.addBankAccount(saveUser.id, firstBankAccountInfo.toEntity(saveUser))
        paymentMethodService.addBankAccount(saveUser.id, secondBankAccountInfo.toEntity(saveUser))
        paymentMethodService.addCard(saveUser.id, thirdCardInfo.toEntity(saveUser))
        paymentMethodService.addCard(saveUser.id, fourthCardInfo.toEntity(saveUser))

        // when
        val deleteResult = paymentMethodService.deleteAllPaymentMethod(saveUser.id)

        // then
        val findList: List<PaymentMethod> = paymentMethodRepository.findAllByUserId(saveUser.id)
        assertThat(deleteResult).isTrue()
        assertThat(findList).hasSize(0)
    }

    @Disabled
    @Test
    fun `사용자가 등록한 결제 수단을 모두 조회한다(기본 결제 수단은 마지막 등록)`() {
        // given
        val user = User(
            name = "newUser",
            phoneNumber = "123-456-789",
            email = "user@gmail.com",
            gender = Gender.MALE,
            status = UserStatus.ACTIVE,
            roadAddress = roadAddress
        )

        val firstBankAccountInfo = RequestAddBankAccount(
            isDefault = true,
            bankAccountNumber = "111-111-111111",
            bankName = Bank.SHINHAN,
            isValid = true,
        )

        val secondBankAccountInfo = RequestAddBankAccount(
            isDefault = true,
            bankAccountNumber = "1002-111-111111",
            bankName = Bank.WOORIE,
            isValid = true,
        )

        val thirdCardInfo = RequestAddCard(
            isDefault = true,
            isValid = true,
            cardHolderName = "KATJ LEE",
            cardNumber = "1212-1212-1212-1212",
            expiryDate = LocalDateTime.now().plusDays(1),
            cvv = "878"
        )

        val fourthCardInfo = RequestAddCard(
            isDefault = false,
            isValid = true,
            cardHolderName = "KATJ LEE",
            cardNumber = "1111-1111-1111-1111",
            expiryDate = LocalDateTime.now().plusDays(1),
            cvv = "787"
        )


        val saveUser = userRepository.save(user)
        paymentMethodService.addBankAccount(saveUser.id, firstBankAccountInfo.toEntity(saveUser))
        paymentMethodService.addBankAccount(saveUser.id, secondBankAccountInfo.toEntity(saveUser))
        val addCard = paymentMethodService.addCard(saveUser.id, thirdCardInfo.toEntity(saveUser))
        paymentMethodService.addCard(saveUser.id, fourthCardInfo.toEntity(saveUser))

        // when
        val paymentMethodList: List<ResponsePaymentMethod> = paymentMethodService.findAllPaymentMethod(saveUser.id)

        // then
        assertThat(paymentMethodList).hasSize(4)

        assertThat(paymentMethodList).extracting("bankAccountNumber")
            .containsExactlyInAnyOrder(firstBankAccountInfo.bankAccountNumber, secondBankAccountInfo.bankAccountNumber, null, null)
        assertThat(paymentMethodList).extracting("bankName")
            .containsExactlyInAnyOrder(firstBankAccountInfo.bankName, secondBankAccountInfo.bankName, null, null)

        assertThat(paymentMethodList).extracting("cardHolderName")
            .containsExactlyInAnyOrder(thirdCardInfo.cardHolderName, fourthCardInfo.cardHolderName, null, null)
        assertThat(paymentMethodList).extracting("cardNumber")
            .containsExactlyInAnyOrder(thirdCardInfo.cardNumber, fourthCardInfo.cardNumber, null, null)
        assertThat(paymentMethodList).extracting("expiryDate")
            .containsExactlyInAnyOrder(thirdCardInfo.expiryDate, fourthCardInfo.expiryDate, null, null)
        assertThat(paymentMethodList).extracting("cvv")
            .containsExactlyInAnyOrder(thirdCardInfo.cvv, fourthCardInfo.cvv, null, null)

        /**
         * 기본 결제 수단은 가장 마지막에 isDefault가 true였던 3번째로 저장한 카드 정보이다
         */
        val defaultExistCheck = paymentMethodReader.isDefaultExistCheck(saveUser.id)
        assertThat(addCard.cardNumber).isEqualTo(defaultExistCheck!!.cardNumber)
    }
}