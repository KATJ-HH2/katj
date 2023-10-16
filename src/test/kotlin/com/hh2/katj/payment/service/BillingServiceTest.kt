package com.hh2.katj.payment.service

import com.hh2.katj.payment.component.PaymentMethodReader
import com.hh2.katj.payment.model.dto.request.RequestAddBankAccount
import com.hh2.katj.payment.model.dto.request.RequestAddCard
import com.hh2.katj.payment.model.entity.Bank
import com.hh2.katj.payment.model.entity.PaymentMethod
import com.hh2.katj.payment.repository.PaymentMethodRepository
import com.hh2.katj.taxi.model.ChargeType
import com.hh2.katj.taxi.model.FuelType
import com.hh2.katj.taxi.model.Taxi
import com.hh2.katj.taxi.repository.TaxiRepository
import com.hh2.katj.taxidriver.model.TaxiDriver
import com.hh2.katj.taxidriver.model.TaxiDriverStatus
import com.hh2.katj.taxidriver.repository.TaxiDriverRepository
import com.hh2.katj.trip.model.DepartureRoadAddress
import com.hh2.katj.trip.model.DestinationRoadAddress
import com.hh2.katj.trip.model.Trip
import com.hh2.katj.trip.model.TripStatus
import com.hh2.katj.trip.repository.TripRepository
import com.hh2.katj.trip.service.BillingService
import com.hh2.katj.user.model.entity.User
import com.hh2.katj.user.model.entity.UserStatus
import com.hh2.katj.user.repository.UserRepository
import com.hh2.katj.util.annotation.KATJTestContainerE2E
import com.hh2.katj.util.model.BaseTestEntity
import com.hh2.katj.util.model.Gender
import com.hh2.katj.util.model.RoadAddress
import org.assertj.core.api.Assertions
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.data.repository.findByIdOrNull
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime

@KATJTestContainerE2E
class BillingServiceTest(
    private val billingService: BillingService,
    private val paymentMethodService: PaymentMethodService,
    private val paymentMethodRepository: PaymentMethodRepository,
    private val paymentMethodReader: PaymentMethodReader,
    private val userRepository: UserRepository,
    private val taxiRepository: TaxiRepository,
    private val taxiDriverRepository: TaxiDriverRepository,
    private val tripRepository: TripRepository,

): BaseTestEntity() {

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

    @Test
    fun `사용자가 기등록한 결제 정보를 사용하여 요금을 지불한다`() {
        // given
        paymentMethodRepository.deleteAllInBatch()

        val driveStartAt = LocalDateTime.now().minusMinutes(20)
        val driveEndAt = LocalDateTime.now()
        val trip = Trip(
            user,
            taxiDriver,
            fare = 25000,
            departure = departure,
            destination = destination,
            driveStartDate = LocalDate.now(),
            driveStartAt = driveStartAt,
            driveEndDate = LocalDate.now(),
            driveEndAt = driveEndAt,
            spentTime = Duration.between(driveStartAt, driveEndAt).toMinutesPart(),
            tripStatus = TripStatus.PAY_REQUEST
        )

        tripRepository.save(trip)

        // when
        val payCompleteTrip = billingService.userPayWithRegiInfo(user.id, trip.id)

        // then
        assertThat(payCompleteTrip.tripStatus).isEqualTo(TripStatus.PAY_COMPLETE)
    }

    @Test
    fun `사용자가 기등록한 결제 정보가 하나도 없다`() {
        // given
        paymentMethodRepository.deleteAllInBatch()

        val driveStartAt = LocalDateTime.now().minusMinutes(20)
        val driveEndAt = LocalDateTime.now()
        val trip = Trip(
            user,
            taxiDriver,
            fare = 25000,
            departure = departure,
            destination = destination,
            driveStartDate = LocalDate.now(),
            driveStartAt = driveStartAt,
            driveEndDate = LocalDate.now(),
            driveEndAt = driveEndAt,
            spentTime = Duration.between(driveStartAt, driveEndAt).toMinutesPart(),
            tripStatus = TripStatus.PAY_REQUEST
        )

        // when // then
        Assertions.assertThatThrownBy {
            billingService.userPayWithRegiInfo(user.id, trip.id)
        }.isInstanceOf(IllegalArgumentException::class.java)
    }

}