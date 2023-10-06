package com.hh2.katj.favorite.component

import com.hh2.katj.favorite.model.dto.RequestAddFavorite
import com.hh2.katj.favorite.model.entity.Favorite
import com.hh2.katj.favorite.repository.FavoriteRepository
import com.hh2.katj.user.model.entity.User
import com.hh2.katj.util.exception.ExceptionMessage.*
import com.hh2.katj.util.exception.failWithMessage
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class FavoriteManager (
        private val favoriteRepository: FavoriteRepository,
) {

    @Transactional(readOnly = true)
    fun findAllFavorite(userId: Long?): MutableList<Favorite> {
        val findAllFavorite = favoriteRepository.findAllByUserId(userId)

        return findAllFavorite
    }

    @Transactional(readOnly = true)
    fun findOneFavorite(userId: Long?, favoriteId: Long?): Favorite {
        val findOneFavorite = favoriteExistValidation(userId, favoriteId)

        return findOneFavorite
    }

    @Transactional
    fun addFavorite(user: User, request: RequestAddFavorite): Favorite {
        // 즐겨찾기 타이틀 중복 체크
        val favoriteTitleDuplicate = favoriteRepository.findByTitle(request.title)

        if (favoriteTitleDuplicate != null) {
            throw IllegalArgumentException(DUPLICATED_DATA_ALREADY_EXISTS.name)
        }

        val favorite: Favorite = Favorite(
                user = user,
                roadAddress = request.roadAddress,
                title = request.title,
                description = request.description
        )

        val savedFavorite = favoriteRepository.save(favorite)

        return savedFavorite
    }

    @Transactional
    fun updateFavorite(userId: Long?, favoriteId: Long?, requestFavorite: Favorite): Favorite {
        favoriteExistValidation(userId, favoriteId)

        val findFavorite = favoriteRepository.findByUserIdAndId(userId, favoriteId)
                ?: failWithMessage(ID_DOES_NOT_EXIST.name)

        findFavorite.update(requestFavorite)

        try {
            findFavorite.update(requestFavorite)
        } catch (e: Exception) {
            throw Exception(INTERNAL_SERVER_ERROR_FROM_DATABASE.name)
        }

        return findFavorite
    }

    @Transactional
    fun deleteOneFavorite(userId: Long?, favoriteId: Long?): Boolean {

        val deleteFavorite = favoriteExistValidation(userId, favoriteId)

        try {
            favoriteRepository.delete(deleteFavorite)
        } catch (e: Exception) {
            throw Exception(INTERNAL_SERVER_ERROR_FROM_DATABASE.name)
        }

        return true
    }

    @Transactional
    fun deleteAllFavorite(userId: Long?): Boolean {

        try {
            favoriteRepository.deleteAllByUserId(userId)
        } catch (e: Exception) {
            throw Exception(INTERNAL_SERVER_ERROR_FROM_DATABASE.name)
        }

        return true
    }

    @Transactional
    fun deleteMultiFavorite(userId: Long?, deleteFavoriteIds: List<Long?>): Boolean {

        try {
            favoriteRepository.deleteFavoritesByUserIdAndIdIn(userId, deleteFavoriteIds)
        } catch (e: Exception) {
            throw Exception(INTERNAL_SERVER_ERROR_FROM_DATABASE.name)
        }

        return true
    }

    /**
     * 즐겨찾기 유효성 체크
     * 해당 유저에게 즐겨찾기가 존재하는지 확인한다
     */
    private fun favoriteExistValidation(userId: Long?, favoriteId: Long?): Favorite =
            favoriteRepository.findByUserIdAndId(userId, favoriteId)
                    ?: failWithMessage(ID_DOES_NOT_EXIST.name)


}