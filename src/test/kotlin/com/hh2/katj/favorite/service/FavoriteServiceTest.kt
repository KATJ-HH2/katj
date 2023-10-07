package com.hh2.katj.favorite.service

import com.hh2.katj.favorite.model.dto.RequestAddFavorite
import com.hh2.katj.favorite.model.dto.RequestUpdateFavorite
import com.hh2.katj.favorite.model.dto.ResponseFavorite
import com.hh2.katj.favorite.model.entity.Favorite
import com.hh2.katj.favorite.repository.FavoriteRepository
import com.hh2.katj.test.IntegrationTest
import com.hh2.katj.user.model.entity.Gender
import com.hh2.katj.user.model.entity.User
import com.hh2.katj.user.model.entity.UserStatus
import com.hh2.katj.user.repository.UserRepository
import com.hh2.katj.util.exception.ExceptionMessage.DUPLICATED_DATA_ALREADY_EXISTS
import com.hh2.katj.util.exception.ExceptionMessage.ID_DOES_NOT_EXIST
import com.hh2.katj.util.exception.failWithMessage
import com.hh2.katj.util.model.RoadAddress
import org.assertj.core.api.Assertions
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestConstructor


@IntegrationTest
class FavoriteServiceTest (
        private val userRepository: UserRepository,
        private val favoriteRepository: FavoriteRepository,
        private val favoriteService: FavoriteService,
){

    @AfterEach
    fun tearUp() {
        userRepository.deleteAllInBatch()
        favoriteRepository.deleteAllInBatch()
    }

    
    @Test
    fun `사용자가 경로를 즐겨찾기에 추가한다`() {
        // given
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
                x = "x.123",
                y = "y.321"
        )

        val user: User = User(
                name = "newUser",
                phoneNumber = "123-456-789",
                email = "user@gmail.com",
                gender = Gender.MALE,
                status = UserStatus.ACTIVE,
                roadAddress = roadAddress
        )

        val favorite: Favorite = Favorite(
                user = user,
                roadAddress = roadAddress,
                title = "favorite_title",
                description = "favorite_description"
        )

        // when
        val saveUser = userRepository.save(user)
        val requestAddFavorite: RequestAddFavorite = RequestAddFavorite(
                roadAddress = roadAddress,
                title = favorite.title,
                description = favorite.description,
                user = saveUser,
                id = null
        )

        // then
        val addFavorite = favoriteService.addFavorite(saveUser.id!!, Favorite.toEntity(requestAddFavorite))

        assertThat(addFavorite.title).isEqualTo(requestAddFavorite.title)
        assertThat(addFavorite.description).isEqualTo(requestAddFavorite.description)
        assertThat(addFavorite.roadAddress).isEqualTo(requestAddFavorite.roadAddress)
    }

    @Test
    fun `사용자가 같은 타이틀의 즐겨찾기를 추가하려고 하면 오류가 발생한다`() {
        // given

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
                x = "x.123",
                y = "y.321"
        )

        val user: User = User(
                name = "newUser",
                phoneNumber = "123-456-789",
                email = "user@gmail.com",
                gender = Gender.MALE,
                status = UserStatus.ACTIVE,
                roadAddress = roadAddress
        )

        val favorite: Favorite = Favorite(
                user = user,
                roadAddress = roadAddress,
                title = "favorite_title",
                description = "favorite_description"
        )

        val saveUser = userRepository.save(user)
        val requestAddFavorite: RequestAddFavorite = RequestAddFavorite(
                roadAddress = roadAddress,
                title = favorite.title,
                description = favorite.description,
                user = saveUser,
                id = null,
        )

        favoriteService.addFavorite(saveUser.id!!, Favorite.toEntity(requestAddFavorite))

        // when
        val duplicatedFavorite: Favorite = Favorite(
                user = user,
                roadAddress = roadAddress,
                title = "favorite_title",
                description = "favorite_duplicated_description"
        )
        val requestDuplicatedFavorite: RequestAddFavorite = RequestAddFavorite(
                roadAddress = roadAddress,
                title = duplicatedFavorite.title,
                description = duplicatedFavorite.description,
                user = user,
                id = null,
        )

        // then
        assertThrows<IllegalArgumentException> {
            favoriteService.addFavorite(saveUser.id!!, Favorite.toEntity(requestDuplicatedFavorite))
        }.apply {
            assertThat(message).isEqualTo(DUPLICATED_DATA_ALREADY_EXISTS.name)
        }
    }

    @Test
    fun `사용자가 등록한 즐겨찾기 중 하나만 선택하여 조회한다`() {
        // given
        val roadAddressA: RoadAddress = RoadAddress(
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
                x = "x.123",
                y = "y.321"
        )

        val user: User = User(
                name = "newUser",
                phoneNumber = "123-456-789",
                email = "user@gmail.com",
                gender = Gender.MALE,
                status = UserStatus.ACTIVE,
                roadAddress = roadAddressA
        )


        val roadAddressB: RoadAddress = RoadAddress(
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
                x = "x.123",
                y = "y.321"
        )

        val favoriteA: Favorite = Favorite(
                user = user,
                roadAddress = roadAddressA,
                title = "favorite_titleA",
                description = "favorite_descriptionA"
        )

        val favoriteB: Favorite = Favorite(
                user = user,
                roadAddress = roadAddressB,
                title = "favorite_titleB",
                description = "favorite_descriptionB"
        )

        // when
        val saveUser = userRepository.save(user)

        val requestAddFavoriteA: RequestAddFavorite = RequestAddFavorite(
                roadAddress = roadAddressA,
                title = favoriteA.title,
                description = favoriteA.description,
                user = saveUser,
                id = null,
        )
        val requestAddFavoriteB: RequestAddFavorite = RequestAddFavorite(
                roadAddress = roadAddressB,
                title = favoriteB.title,
                description = favoriteB.description,
                user = saveUser,
                id = null
        )
        favoriteService.addFavorite(saveUser.id!!, Favorite.toEntity(requestAddFavoriteA))
        favoriteService.addFavorite(saveUser.id!!, Favorite.toEntity(requestAddFavoriteB))

        val favorites: List<ResponseFavorite> = favoriteService.findAllFavorite(saveUser.id!!)
        val findFavoriteA = favorites.first { it.title == requestAddFavoriteA.title }

        // then

        val findOneFavorite = favoriteService.findOneFavorite(saveUser.id!!, findFavoriteA.id!!)

        assertThat(findOneFavorite.roadAddress).isEqualTo(requestAddFavoriteA.roadAddress)
        assertThat(findOneFavorite.title).isEqualTo(requestAddFavoriteA.title)
        assertThat(findOneFavorite.description).isEqualTo(requestAddFavoriteA.description)

    }

    @Test
    fun `사용자가 즐겨찾기를 전부 조회한다`() {
        // given
        val roadAddressA: RoadAddress = RoadAddress(
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
                x = "x.123",
                y = "y.321"
        )

        val user: User = User(
                name = "newUser",
                phoneNumber = "123-456-789",
                email = "user@gmail.com",
                gender = Gender.MALE,
                status = UserStatus.ACTIVE,
                roadAddress = roadAddressA
        )


        val roadAddressB: RoadAddress = RoadAddress(
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
                x = "x.123",
                y = "y.321"
        )

        val favoriteA: Favorite = Favorite(
                user = user,
                roadAddress = roadAddressA,
                title = "favorite_titleA",
                description = "favorite_descriptionA"
        )

        val favoriteB: Favorite = Favorite(
                user = user,
                roadAddress = roadAddressB,
                title = "favorite_titleB",
                description = "favorite_descriptionB"
        )

        // when
        val saveUser = userRepository.save(user)

        val requestAddFavoriteA: RequestAddFavorite = RequestAddFavorite(
                roadAddress = roadAddressA,
                title = favoriteA.title,
                description = favoriteA.description,
                user = saveUser,
                id = null,
        )
        val requestAddFavoriteB: RequestAddFavorite = RequestAddFavorite(
                roadAddress = roadAddressB,
                title = favoriteB.title,
                description = favoriteB.description,
                user = saveUser,
                id = null,
        )
        favoriteService.addFavorite(saveUser.id!!, Favorite.toEntity(requestAddFavoriteA))
        favoriteService.addFavorite(saveUser.id!!, Favorite.toEntity(requestAddFavoriteB))

        val favorites: List<ResponseFavorite> = favoriteService.findAllFavorite(saveUser.id!!)

        // then
        Assertions.assertThat(favorites.size).isEqualTo(2)

        assertThat(favorites).extracting("roadAddress")
                .containsExactlyInAnyOrder(requestAddFavoriteA.roadAddress, requestAddFavoriteB.roadAddress)
        assertThat(favorites).extracting("title")
                .containsExactlyInAnyOrder(requestAddFavoriteA.title, requestAddFavoriteB.title)
        assertThat(favorites).extracting("description")
                .containsExactlyInAnyOrder(requestAddFavoriteA.description, requestAddFavoriteB.description)

    }

    /**
     * request에는 변경된 정보, 변경되지 않을 정보 모두 담겨있어야 한다.
     */
    @Test
    fun `사용자가 즐겨찾기 정보를 수정한다`() {
        // given
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
                x = "x.123",
                y = "y.321"
        )

        val user: User = User(
                name = "newUser",
                phoneNumber = "123-456-789",
                email = "user@gmail.com",
                gender = Gender.MALE,
                status = UserStatus.ACTIVE,
                roadAddress = roadAddress
        )

        val favorite: Favorite = Favorite(
                user = user,
                roadAddress = roadAddress,
                title = "favorite_title",
                description = "favorite_description"
        )

        val saveUser = userRepository.save(user)
        val requestAddFavorite: RequestAddFavorite = RequestAddFavorite(
                roadAddress = roadAddress,
                title = favorite.title,
                description = favorite.description,
                user = saveUser,
                id = null
        )

        favoriteService.addFavorite(saveUser.id!!, Favorite.toEntity(requestAddFavorite))
        val savedFavorite = favoriteRepository.findByTitle(requestAddFavorite.title) ?: failWithMessage(ID_DOES_NOT_EXIST.name)

        // when
        val newRoadAddress: RoadAddress = RoadAddress(
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
                x = "x.123",
                y = "y.321"
        )

        val requestUpdateFavorite = RequestUpdateFavorite(
            id = null,
            user = user,
            roadAddress = newRoadAddress,
            title = "update_favorite_title",
            description = "update_favorite_description"
        )
        val updatedFavorite = favoriteService.updateFavorite(savedFavorite.user.id!!, savedFavorite.id!!, Favorite.toEntity(requestUpdateFavorite))


        // then
        assertThat(updatedFavorite.title).isEqualTo(requestUpdateFavorite.title)
        assertThat(updatedFavorite.description).isEqualTo(requestUpdateFavorite.description)
        assertThat(updatedFavorite.roadAddress).isEqualTo(requestUpdateFavorite.roadAddress)
        assertThat(updatedFavorite.user).isEqualTo(requestUpdateFavorite.user)
    }

    @Test
    fun `사용자가 즐겨찾기 하나를 삭제한다`() {
        // given
        val roadAddressA: RoadAddress = RoadAddress(
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
                x = "x.123",
                y = "y.321"
        )

        val user: User = User(
                name = "newUser",
                phoneNumber = "123-456-789",
                email = "user@gmail.com",
                gender = Gender.MALE,
                status = UserStatus.ACTIVE,
                roadAddress = roadAddressA
        )


        val roadAddressB: RoadAddress = RoadAddress(
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
                x = "x.123",
                y = "y.321"
        )

        val favoriteA: Favorite = Favorite(
                user = user,
                roadAddress = roadAddressA,
                title = "favorite_titleA",
                description = "favorite_descriptionA"
        )

        val favoriteB: Favorite = Favorite(
                user = user,
                roadAddress = roadAddressB,
                title = "favorite_titleB",
                description = "favorite_descriptionB"
        )

        // when
        val saveUser = userRepository.save(user)

        val requestAddFavoriteA: RequestAddFavorite = RequestAddFavorite(
                roadAddress = roadAddressA,
                title = favoriteA.title,
                description = favoriteA.description,
                user = saveUser,
                id = null,
        )
        val requestAddFavoriteB: RequestAddFavorite = RequestAddFavorite(
                roadAddress = roadAddressB,
                title = favoriteB.title,
                description = favoriteB.description,
                user = saveUser,
                id = null,
        )
        favoriteService.addFavorite(saveUser.id!!, Favorite.toEntity(requestAddFavoriteA))
        favoriteService.addFavorite(saveUser.id!!, Favorite.toEntity(requestAddFavoriteB))



        val beforeDeleteFavorites: List<ResponseFavorite> = favoriteService.findAllFavorite(saveUser.id!!)

        val deleteFavorite = beforeDeleteFavorites.first { it.title == favoriteB.title }
        val deleteResult: Boolean = favoriteService.deleteOneFavorite(saveUser.id!!, deleteFavorite.id!!)

        val afterDeleteFavorites: List<ResponseFavorite> = favoriteService.findAllFavorite(saveUser.id!!)

        // then
        assertThat(afterDeleteFavorites.size).isEqualTo(1)
        assertThat(deleteResult).isTrue()

        assertThat(afterDeleteFavorites).extracting("roadAddress")
                .containsExactlyInAnyOrder(requestAddFavoriteA.roadAddress)
        assertThat(afterDeleteFavorites).extracting("title")
                .containsExactlyInAnyOrder(requestAddFavoriteA.title)
        assertThat(afterDeleteFavorites).extracting("description")
                .containsExactlyInAnyOrder(requestAddFavoriteA.description)
    }

    @Test
    fun `사용자가 즐겨찾기를 전부 삭제한다`() {
        // given
        val roadAddressA: RoadAddress = RoadAddress(
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
                x = "x.123",
                y = "y.321"
        )

        val user: User = User(
                name = "newUser",
                phoneNumber = "123-456-789",
                email = "user@gmail.com",
                gender = Gender.MALE,
                status = UserStatus.ACTIVE,
                roadAddress = roadAddressA
        )


        val roadAddressB: RoadAddress = RoadAddress(
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
                x = "x.123",
                y = "y.321"
        )

        val favoriteA: Favorite = Favorite(
                user = user,
                roadAddress = roadAddressA,
                title = "favorite_titleA",
                description = "favorite_descriptionA"
        )

        val favoriteB: Favorite = Favorite(
                user = user,
                roadAddress = roadAddressB,
                title = "favorite_titleB",
                description = "favorite_descriptionB"
        )

        // when
        val saveUser = userRepository.save(user)

        val requestAddFavoriteA: RequestAddFavorite = RequestAddFavorite(
                roadAddress = roadAddressA,
                title = favoriteA.title,
                description = favoriteA.description,
                user = saveUser,
                id = null,
        )
        val requestAddFavoriteB: RequestAddFavorite = RequestAddFavorite(
                roadAddress = roadAddressB,
                title = favoriteB.title,
                description = favoriteB.description,
                user = saveUser,
                id = null
        )
        favoriteService.addFavorite(saveUser.id!!, Favorite.toEntity(requestAddFavoriteA))
        favoriteService.addFavorite(saveUser.id!!, Favorite.toEntity(requestAddFavoriteB))



        val beforeDeleteFavorites: List<ResponseFavorite> = favoriteService.findAllFavorite(saveUser.id!!)

        val deleteAllResult: Boolean = favoriteService.deleteAllFavorite(saveUser.id!!)

        val afterDeleteFavorites: List<ResponseFavorite> = favoriteService.findAllFavorite(saveUser.id!!)

        // then

        assertThat(beforeDeleteFavorites).hasSize(2)
        assertThat(afterDeleteFavorites).hasSize(0)
        assertThat(deleteAllResult).isTrue()
    }

    @Test
    fun `사용자가 선택한 여러개의 즐겨찾기를 삭제한다`() {
        // given
        val roadAddressA: RoadAddress = RoadAddress(
                addressName = "address_nameA",
                region1depthName = "r_1",
                region2depthName = "r_2",
                region3depthName = "r_3",
                roadName = "road_name",
                undergroundYn = "Y",
                mainBuildingNo = "1",
                subBuildingNo = "2",
                buildingName = "bn",
                zoneNo = "11232",
                x = "x.123",
                y = "y.321"
        )

        val user: User = User(
                name = "newUser",
                phoneNumber = "123-456-789",
                email = "user@gmail.com",
                gender = Gender.MALE,
                status = UserStatus.ACTIVE,
                roadAddress = roadAddressA
        )


        val roadAddressB: RoadAddress = RoadAddress(
                addressName = "address_nameB",
                region1depthName = "r_1",
                region2depthName = "r_2",
                region3depthName = "r_3",
                roadName = "road_name",
                undergroundYn = "Y",
                mainBuildingNo = "1",
                subBuildingNo = "2",
                buildingName = "bn",
                zoneNo = "11232",
                x = "x.123",
                y = "y.321"
        )

        val roadAddressC: RoadAddress = RoadAddress(
                addressName = "address_nameC",
                region1depthName = "r_1",
                region2depthName = "r_2",
                region3depthName = "r_3",
                roadName = "road_name",
                undergroundYn = "Y",
                mainBuildingNo = "1",
                subBuildingNo = "2",
                buildingName = "bn",
                zoneNo = "11232",
                x = "x.123",
                y = "y.321"
        )

        val favoriteA: Favorite = Favorite(
                user = user,
                roadAddress = roadAddressA,
                title = "favorite_titleA",
                description = "favorite_descriptionA"
        )

        val favoriteB: Favorite = Favorite(
                user = user,
                roadAddress = roadAddressB,
                title = "favorite_titleB",
                description = "favorite_descriptionB"
        )

        val favoriteC: Favorite = Favorite(
                user = user,
                roadAddress = roadAddressC,
                title = "favorite_titleC",
                description = "favorite_descriptionC"
        )

        // when
        val saveUser = userRepository.save(user)

        val requestAddFavoriteA: RequestAddFavorite = RequestAddFavorite(
                roadAddress = roadAddressA,
                title = favoriteA.title,
                description = favoriteA.description,
                user = saveUser,
                id = null,
        )
        val requestAddFavoriteB: RequestAddFavorite = RequestAddFavorite(
                roadAddress = roadAddressB,
                title = favoriteB. title,
                description = favoriteB.description,
                user = saveUser,
                id = null,
        )
        val requestAddFavoriteC: RequestAddFavorite = RequestAddFavorite(
                roadAddress = roadAddressC,
                title = favoriteC.title,
                description = favoriteC.description,
                user = saveUser,
                id = null,
        )

        favoriteService.addFavorite(saveUser.id!!, Favorite.toEntity(requestAddFavoriteA))
        favoriteService.addFavorite(saveUser.id!!, Favorite.toEntity(requestAddFavoriteB))
        favoriteService.addFavorite(saveUser.id!!, Favorite.toEntity(requestAddFavoriteC))



        val beforeDeleteFavorites: List<ResponseFavorite> = favoriteService.findAllFavorite(saveUser.id!!)

        val deleteFavoriteList = beforeDeleteFavorites.filter { it.title != favoriteA.title }.map { it.id!! }
        val deleteResult: Boolean = favoriteService.deleteMultiFavorite(saveUser.id!!, deleteFavoriteList)

        val afterDeleteFavorites: List<ResponseFavorite> = favoriteService.findAllFavorite(saveUser.id!!)

        // then

        assertThat(beforeDeleteFavorites).hasSize(3)
        assertThat(afterDeleteFavorites).hasSize(1)
        assertThat(deleteResult).isTrue()
    }



}