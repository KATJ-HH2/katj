package com.hh2.katj.favorite.component

import com.hh2.katj.favorite.model.entity.Favorite
import com.hh2.katj.favorite.repository.FavoriteRepository
import com.hh2.katj.util.exception.ExceptionMessage.*
import com.hh2.katj.util.exception.failWithMessage
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class FavoriteManager (
    private val favoriteRepository: FavoriteRepository,
) {

    @Transactional
    fun addFavorite(request: Favorite): Favorite {
        // 즐겨찾기 타이틀 중복 체크
        val favoriteTitleDuplicate = favoriteRepository.findByTitle(request.title)

        if (favoriteTitleDuplicate != null) {
            throw IllegalArgumentException(DUPLICATED_DATA_ALREADY_EXISTS.name)
        }

        val savedFavorite = favoriteRepository.save(request)

        return savedFavorite
    }

    @Transactional
    fun updateFavorite(favoriteId: Long, request: Favorite): Favorite {
        val findFavorite = favoriteExistValidation(request.user.id, favoriteId)

        try {
            findFavorite.update(request)
        } catch (e: Exception) {
            throw RuntimeException(INTERNAL_SERVER_ERROR_FROM_DATABASE.name)
        }

        return findFavorite
    }

    @Transactional
    fun deleteOneFavorite(userId: Long, favoriteId: Long): Boolean {

        val deleteFavorite = favoriteExistValidation(userId, favoriteId)

        try {
            favoriteRepository.delete(deleteFavorite)
        } catch (e: Exception) {
            throw RuntimeException(INTERNAL_SERVER_ERROR_FROM_DATABASE.name)
        }

        return true
    }

    @Transactional
    fun deleteAllFavorite(userId: Long): Boolean {

        try {
            favoriteRepository.deleteAllByUserId(userId)
        } catch (e: Exception) {
            throw RuntimeException(INTERNAL_SERVER_ERROR_FROM_DATABASE.name)
        }

        return true
    }

    @Transactional
    fun deleteMultiFavorite(userId: Long, deleteFavoriteIds: List<Long>): Boolean {

        val deleteRowCount = favoriteRepository.deleteFavoritesByUserIdAndIdIn(userId, deleteFavoriteIds)

        if (deleteRowCount != deleteFavoriteIds.size) {
            throw RuntimeException(INTERNAL_SERVER_ERROR_FROM_DATABASE.name)
        }

        return true
    }

    private fun favoriteExistValidation(userId: Long, favoriteId: Long): Favorite =
        favoriteRepository.findByUserIdAndId(userId, favoriteId)
            ?: failWithMessage(ID_DOES_NOT_EXIST.name)

}