package com.hh2.katj.favorite.component

import com.hh2.katj.favorite.model.Favorite
import com.hh2.katj.favorite.model.RequestFavorite
import com.hh2.katj.favorite.repository.FavoriteRepository
import com.hh2.katj.user.model.User
import com.hh2.katj.user.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.lang.IllegalArgumentException

@Component
@Transactional(readOnly = true)
class FavoriteReader @Autowired constructor(
        private val favoriteRepository: FavoriteRepository,
        private val userRepository: UserRepository

) {

    @Transactional
    fun addFavorite(userId: Long?, request: RequestFavorite): Boolean {
        // user 찾기 실패시 오류 반환 또는 false 반환 정책 정해야 할듯
        val findUser = userValidation(userId)

        // 즐겨찾기 타이틀 중복 체크
        val favoriteTitleDuplicate = favoriteRepository.findByTitle(request.title).isEmpty

        if (!favoriteTitleDuplicate) {
            // 중복시 오류 반환? false 반환?
            return false
        }

        val favorite = Favorite(
                user = findUser,
                roadAddress = request.roadAddress,
                title = request.title,
                description = request.description
        )

        favoriteRepository.save(favorite)

        return true
    }

    fun findAllFavorite(userId: Long?): MutableList<Favorite> {
        userValidation(userId)

        val findAllFavorite = favoriteRepository.findAll()

        return findAllFavorite
    }

    /**
     * 유저 유효성 체크
     */
    private fun userValidation(userId: Long?): User {
        return userRepository.findByIdOrNull(userId) ?: throw IllegalArgumentException("user doesn't exist")
    }

}