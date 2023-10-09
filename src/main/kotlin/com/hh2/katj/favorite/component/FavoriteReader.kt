package com.hh2.katj.favorite.component

import com.hh2.katj.favorite.model.entity.Favorite
import com.hh2.katj.favorite.repository.FavoriteRepository
import com.hh2.katj.util.exception.ExceptionMessage.ID_DOES_NOT_EXIST
import com.hh2.katj.util.exception.failWithMessage
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class FavoriteReader (
    private val favoriteRepository: FavoriteRepository,
) {
    @Transactional(readOnly = true)
    fun findAllFavorite(userId: Long): MutableList<Favorite> {
        val findAllFavorite = favoriteRepository.findAllByUserId(userId)

        return findAllFavorite
    }

    @Transactional(readOnly = true)
    fun findOneFavorite(userId: Long, favoriteId: Long): Favorite {
        val findOneFavorite = favoriteExistValidation(userId, favoriteId)

        return findOneFavorite
    }

    /**
     * 즐겨찾기 유효성 체크
     * 해당 유저에게 즐겨찾기가 존재하는지 확인한다
     */
    private fun favoriteExistValidation(userId: Long, favoriteId: Long): Favorite =
        favoriteRepository.findByUserIdAndId(userId, favoriteId)
            ?: failWithMessage(ID_DOES_NOT_EXIST.name)

}