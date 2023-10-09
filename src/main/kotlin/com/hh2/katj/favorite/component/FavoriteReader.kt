package com.hh2.katj.favorite.component

import com.hh2.katj.favorite.model.entity.Favorite
import com.hh2.katj.favorite.model.dto.request.RequestAddFavorite
import com.hh2.katj.favorite.repository.FavoriteRepository
import com.hh2.katj.user.model.entity.User
import com.hh2.katj.user.repository.UserRepository
import com.hh2.katj.util.exception.ExceptionMessage.*
import com.hh2.katj.util.exception.failWithMessage
import com.hh2.katj.util.exception.findByIdOrThrowMessage
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.lang.IllegalArgumentException

@Component
class FavoriteReader (
    private val favoriteRepository: FavoriteRepository,
    private val userRepository: UserRepository,
) {

    @Transactional
    fun addFavorite(userId: Long?, request: RequestAddFavorite): Favorite {
        // user 찾기 실패시 오류 반환 또는 false 반환 정책 정해야 할듯
        val findUser = userValidation(userId)

        // 즐겨찾기 타이틀 중복 체크
        val favoriteTitleDuplicate = favoriteRepository.findByTitle(request.title)

        if (favoriteTitleDuplicate != null) {
            throw IllegalArgumentException(DUPLICATED_DATA_ALREADY_EXISTS.name)
        }

        val favorite: Favorite = Favorite(
                user = findUser,
                roadAddress = request.roadAddress,
                title = request.title,
                description = request.description
        )

        val savedFavorite = favoriteRepository.save(favorite)

        return savedFavorite
    }

    @Transactional(readOnly = true)
    fun findAllFavorite(userId: Long?): MutableList<Favorite> {
        userValidation(userId)

        val findAllFavorite = favoriteRepository.findAll()

        return findAllFavorite
    }

    @Transactional(readOnly = true)
    fun findOneFavorite(userId: Long?, favoriteId: Long?): Favorite {
        userValidation(userId)

        val findOneFavorite = favoriteRepository.findByUserIdAndId(userId, favoriteId)
                ?: failWithMessage(ID_DOES_NOT_EXIST.name)

        return findOneFavorite
    }

    /**
     * 유저 유효성 체크
     */
    private fun userValidation(userId: Long?): User {
        return userRepository.findByIdOrThrowMessage(userId, USER_DOES_NOT_EXIST.name)
    }

}