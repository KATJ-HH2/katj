package com.hh2.katj.favorite.service

import com.hh2.katj.favorite.component.FavoriteReader
import com.hh2.katj.favorite.model.dto.RequestAddFavorite
import com.hh2.katj.favorite.model.entity.Favorite
import com.hh2.katj.user.component.UserReader
import com.hh2.katj.user.model.entity.User
import org.springframework.stereotype.Service

@Service
class FavoriteService (
        private val favoriteReader: FavoriteReader,
        private val userReader: UserReader,
){
    fun addFavorite(userId: Long?, request: RequestAddFavorite): Favorite {
        val validatedUser = userValidation(userId)

        val addedFavorite = favoriteReader.addFavorite(validatedUser, request)
        // 저장후 Component 에서 결과를 받아야 함
        return addedFavorite
    }

    fun findAllFavorite(userId: Long?): MutableList<Favorite> {
        val validatedUser = userValidation(userId)

        val findAllFavorite = favoriteReader.findAllFavorite(validatedUser.id)
        return findAllFavorite
    }

    fun findOneFavorite(userId: Long?, favoriteId: Long?): Favorite {
        val validatedUser = userValidation(userId)

        val findFavorite = favoriteReader.findOneFavorite(validatedUser.id, favoriteId)
        return findFavorite
    }

    fun updateFavorite(userId: Long?, favoriteId: Long?, request: Favorite): Favorite {
        val validatedUser = userValidation(userId)

        val updatedFavorite = favoriteReader.updateFavorite(validatedUser.id, favoriteId, request)
        return updatedFavorite
    }

    fun deleteOneFavorite(userId: Long?, favoriteId: Long?): Boolean {
        val validatedUser = userValidation(userId)

        val deleteResult = favoriteReader.deleteOneFavorite(validatedUser.id, favoriteId)
        return deleteResult
    }

    fun deleteAllFavorite(userId: Long?): Boolean {
        val validatedUser = userValidation(userId)

        val deleteAllResult = favoriteReader.deleteAllFavorite(validatedUser.id)
        return deleteAllResult
    }

    fun deleteMultiFavorite(userId: Long?, deleteFavoriteList: List<Favorite>): Boolean {
        val validatedUser = userValidation(userId)

        val deleteFavoriteIds = deleteFavoriteList.map { it.id }.toMutableList()
        val deleteMultiFavoriteResult = favoriteReader.deleteMultiFavorite(validatedUser.id, deleteFavoriteIds)
        return deleteMultiFavoriteResult
    }

    /**
     * 유저 유효성 체크
     */
    private fun userValidation(userId: Long?): User =
            userReader.userValidation(userId)
}