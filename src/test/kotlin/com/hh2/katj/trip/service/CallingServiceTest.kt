package com.hh2.katj.trip.service

import com.hh2.katj.payment.model.dto.request.RequestAddBankAccount
import com.hh2.katj.payment.model.dto.request.RequestAddCard
import com.hh2.katj.payment.model.entity.Bank
import com.hh2.katj.payment.model.entity.PaymentMethod
import com.hh2.katj.payment.repository.PaymentMethodRepository
import com.hh2.katj.payment.service.PaymentMethodService
import com.hh2.katj.taxi.model.ChargeType
import com.hh2.katj.taxi.model.FuelType
import com.hh2.katj.taxi.model.Taxi
import com.hh2.katj.taxi.repository.TaxiRepository
import com.hh2.katj.taxidriver.model.TaxiDriver
import com.hh2.katj.taxidriver.model.TaxiDriverStatus
import com.hh2.katj.taxidriver.repository.TaxiDriverRepository
import com.hh2.katj.trip.model.DepartureRoadAddress
import com.hh2.katj.trip.model.DestinationRoadAddress
import com.hh2.katj.trip.model.TripStatus
import com.hh2.katj.trip.model.request.RequestTrip
import com.hh2.katj.trip.model.response.ResponseTrip
import com.hh2.katj.trip.repository.TripRepository
import com.hh2.katj.user.model.entity.User
import com.hh2.katj.user.model.entity.UserStatus
import com.hh2.katj.user.repository.UserRepository
import com.hh2.katj.util.annotation.KATJTestContainerE2E
import com.hh2.katj.util.exception.ExceptionMessage
import com.hh2.katj.util.model.Gender
import com.hh2.katj.util.model.RoadAddress
import org.assertj.core.api.Assertions
import org.assertj.core.api.AssertionsForInterfaceTypes.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.data.repository.findByIdOrNull
import java.time.LocalDate
import java.time.LocalDateTime

@KATJTestContainerE2E
class CallingServiceTest(
    private val paymentMethodService: PaymentMethodService,
    private val callingService: CallingService,
    private val paymentMethodRepository: PaymentMethodRepository,
    private val userRepository: UserRepository,
    private val taxiRepository: TaxiRepository,
    private val taxiDriverRepository: TaxiDriverRepository,
    private val tripRepository: TripRepository,
) {

    lateinit var bankAccount_enough: PaymentMethod
    lateinit var card_enough: PaymentMethod
    lateinit var bankAccount_not_enough: PaymentMethod
    lateinit var card_not_enough: PaymentMethod
    lateinit var user: User
    lateinit var taxiDriver: TaxiDriver
    var departure = DepartureRoadAddress(
        departureAddressName = "address_name",
        departureRegion1depthName = "r_1",
        departureRegion2depthName = "r_2",
        departureRegion3depthName = "r_3",
        departureRoadName = "road_name",
        departureUndergroundYn = "Y",
        departureMainBuildingNo = "1",
        departureSubBuildingNo = "2",
        departureBuildingName = "bn",
        departureZoneNo = "11232",
        departureLongitude = "x.123",
        departureLatitude = "y.321",
    )
    var destination = DestinationRoadAddress(
        destinationAddressName = "address_name",
        destinationRegion1depthName = "r_1",
        destinationRegion2depthName = "r_2",
        destinationRegion3depthName = "r_3",
        destinationRoadName = "road_name",
        destinationUndergroundYn = "Y",
        destinationMainBuildingNo = "1",
        destinationSubBuildingNo = "2",
        destinationBuildingName = "bn",
        destinationZoneNo = "11232",
        destinationLongitude = "x.123",
        destinationLatitude = "y.321",
    )

    @BeforeEach
    fun setUp() {
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
        val saveUser = User(
            name = "newUser",
            phoneNumber = "123-456-789",
            email = "user@gmail.com",
            gender = Gender.MALE,
            status = UserStatus.ACTIVE,
            roadAddress = roadAddress
        )
        val taxi = Taxi(
            carNo = "taxi_num",
            kind = ChargeType.NORMAL,
            vin = "123A456BC",
            manufactureDate = LocalDate.now().minusYears(4),
            fuel = FuelType.GASOLINE,
            color = "RED",
            insureYN = true,
            accidentYN = false,
        )
        taxiDriver = TaxiDriver(
            taxi = taxi,
            driverLicenseId = "driver_license_id",
            issueDate = LocalDate.now().minusYears(5),
            securityId = "security_id",
            name = "Tom",
            status = TaxiDriverStatus.STARTDRIVE,
            gender = Gender.UNKNOWN,
            address = roadAddress,
            img = "123"
        )

        val firstBankAccountInfo = RequestAddBankAccount(
            isDefault = false,
            isValid = true,
            bankAccountNumber = "111-111-111111",
            bankName = Bank.SHINHAN,
        )

        val secondBankAccountInfo = RequestAddBankAccount(
            isDefault = false,
            isValid = true,
            bankAccountNumber = "121-111-111111",
            bankName = Bank.SHINHAN,
        )

        val thirdCardInfo = RequestAddCard(
            isDefault = true,
            isValid = true,
            cardHolderName = "KATJ LEE",
            cardNumber = "1212-1212-1212-1212",
            expiryDate = LocalDate.now().plusDays(1),
            cvv = "878"
        )

        val fourthCardInfo = RequestAddCard(
            isDefault = false,
            isValid = true,
            cardHolderName = "KATJ LEE",
            cardNumber = "1111-1111-1111-1111",
            expiryDate = LocalDate.now().plusDays(1),
            cvv = "787"
        )

        user = userRepository.save(saveUser)
        taxiRepository.save(taxi)
        taxiDriver = taxiDriverRepository.save(taxiDriver)

        val bankAccount1 = paymentMethodService.addBankAccount(user.id, firstBankAccountInfo.toEntity(user))
        val bankAccount2 = paymentMethodService.addBankAccount(user.id, secondBankAccountInfo.toEntity(user))
        val card1 = paymentMethodService.addCard(user.id, thirdCardInfo.toEntity(user))
        val card2 = paymentMethodService.addCard(user.id, fourthCardInfo.toEntity(user))

        bankAccount_enough = paymentMethodRepository.findByIdOrNull(bankAccount1.id)!!
        bankAccount_not_enough = paymentMethodRepository.findByIdOrNull(bankAccount2.id)!!
        card_enough = paymentMethodRepository.findByIdOrNull(card1.id)!!
        card_not_enough = paymentMethodRepository.findByIdOrNull(card2.id)!!
    }


    @AfterEach
    fun tearDown() {
        tripRepository.deleteAllInBatch()
        taxiRepository.deleteAllInBatch()
        taxiDriverRepository.deleteAllInBatch()
        paymentMethodRepository.deleteAllInBatch()
        userRepository.deleteAllInBatch()
    }

    //택시 호출 및 해당 택시 정보 수신
    @Test
    fun `사용자가 검색한 위치 정보를 가지고 택시를 호출`() {
        // given
        val requestCreateTripByUser: RequestTrip = RequestTrip(
            user = user,
            taxiDriver = taxiDriver,
            departure = departure,
            fare = 5000,
            destination = destination,
            driveStartDate = LocalDate.now(),
            driveStartAt = LocalDateTime.now(),
            spentTime = 12000000,
            tripStatus = TripStatus.CALL_TAXI,
        )

        val requestTrip = requestCreateTripByUser.toEntity()

        // when
        val responseTrip: ResponseTrip = callingService.callTaxiByUser(requestTrip)

        // then
        assertThat(responseTrip.tripStatus).isEqualTo(TripStatus.CALL_TAXI)
    }

    @Test
    fun `택시 호출 요청시 호출의 상태가 CALL_TAXI가 아니면 호출에 실패한다`() {
        // given
        val requestCreateTripByUser: RequestTrip = RequestTrip(
            user = user,
            taxiDriver = taxiDriver,
            departure = departure,
            fare = 5000,
            destination = destination,
            driveStartDate = LocalDate.now(),
            driveStartAt = LocalDateTime.now(),
            spentTime = 12000000,
            tripStatus = TripStatus.ASSIGN_TAXI,
        )

        val requestTrip = requestCreateTripByUser.toEntity()

        // when //then
        assertThrows<IllegalArgumentException> {
            callingService.callTaxiByUser(requestTrip)
        }.apply {
            Assertions.assertThat(message).isEqualTo(ExceptionMessage.INCORRECT_STATUS_VALUE.name)
        }
    }

    // 호출한 택시 정보를 수신 한다
    @Test
    fun `사용자가 호출한 택시 정보를 수신한다`() {
        // given
        val requestCreateTripByUser: RequestTrip = RequestTrip(
            user = user,
            taxiDriver = taxiDriver,
            departure = departure,
            fare = 5000,
            destination = destination,
            driveStartDate = LocalDate.now(),
            driveStartAt = LocalDateTime.now(),
            spentTime = 12000000,
            tripStatus = TripStatus.ASSIGN_TAXI,
        )

        val requestTrip = requestCreateTripByUser.toEntity()


        // then
    }

}