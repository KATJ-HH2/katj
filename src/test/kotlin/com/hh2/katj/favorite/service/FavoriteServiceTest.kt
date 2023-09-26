package com.hh2.katj.favorite.service

import com.hh2.katj.cmn.model.RoadAddress
import com.hh2.katj.favorite.model.Favorite
import com.hh2.katj.favorite.model.RequestFavorite
import com.hh2.katj.favorite.repository.FavoriteRepository
import com.hh2.katj.user.model.User
import com.hh2.katj.user.repository.UserRepository
import com.hh2.katj.user.util.Gender
import com.hh2.katj.user.util.UserStatus
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class FavoriteServiceTest @Autowired constructor(
        private val userRepository: UserRepository,
        private val favoriteService: FavoriteService
){

    @Test
    fun `사용자가_경로를_즐겨찾기에_추가한다`() {
        // given
        val user: User = User(
                name = "newUser",
                phoneNumber = "123-456-789",
                email = "user@gmail.com",
                gender = Gender.MALE,
                status = UserStatus.ACTIVE
        )

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

        val favorite: Favorite = Favorite(
                user = user,
                roadAddress = roadAddress,
                title = "favorite_title",
                description = "favorite_description"
        )
        
        // when
        val saveUser = userRepository.save(user)
        val requestFavorite: RequestFavorite = RequestFavorite(
                roadAddress = roadAddress,
                favorite.title,
                favorite.description
        )

        // then
        val result: Boolean = favoriteService.addFavorite(saveUser.id, requestFavorite)
        Assertions.assertThat(result).isTrue()
    }
    
}