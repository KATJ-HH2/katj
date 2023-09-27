package com.hh2.katj.domain.user.service

import com.hh2.katj.domain.favorite.component.FavoriteReader
import com.hh2.katj.domain.user.component.UserSpec
import com.hh2.katj.domain.favorite.model.Favorite
import com.hh2.katj.domain.favorite.repository.FavoriteRepository
import com.hh2.katj.domain.favorite.service.FavoriteService
import com.hh2.katj.domain.user.model.Gender
import com.hh2.katj.domain.user.model.User
import com.hh2.katj.domain.user.repository.UserRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestConstructor

@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class UserFavoriteServiceTest(
    private val userService: UserService,
    private val favoriteService: FavoriteService,
    private val userSpec: UserSpec,
    private val favoriteReader: FavoriteReader,
    private val userRepository: UserRepository,
    private val favoriteRepository: FavoriteRepository,
) {

    val newUser = User(
        email = "jeff3877@naver.com",
        phoneNumber = "01032535576",
        userName = "탁지성",
        gender = Gender.MALE,
    )

    @AfterEach
    fun tearUp() {
        userRepository.deleteAllInBatch()
        favoriteRepository.deleteAllInBatch()
    }

    @Test
    fun `사용자는 장소를 즐겨찾기로 등록할 수 있다`() {
        //given
        val favorite = Favorite(
            user = newUser,
            title = "집",
            description = "집입니다.",
        )

        // when
        val saveUser = userSpec.saveUser(newUser)

        // then
        val favoriteId = userService.addFavoritePlace(saveUser.id, favorite)
//        val findFavorite = favoriteService.findFavoriteByUserId(1)
//        val findUser = favoriteReader.findUserByFavorite(findFavorite)

        assertThat(favoriteId).isEqualTo(1)
    }
}
