package com.hh2.katj.favorite.service

import com.hh2.katj.favorite.component.FavoriteManager
import com.hh2.katj.favorite.model.dto.RequestAddFavorite
import com.hh2.katj.favorite.model.dto.ResponseFavorite
import com.hh2.katj.favorite.model.entity.Favorite
import com.hh2.katj.user.component.UserManager
import com.hh2.katj.user.model.entity.User
import org.springframework.stereotype.Service

/**
 * Controller Layer 에서 Service 호출 시 User 에 대한 검증이
 * 이미 끝났다는 가정 하에 Service에서는 유저의 DB 유효성만 체크하게 되어있음
 */
@Service
class FavoriteService (
        private val favoriteManager: FavoriteManager,
        private val userManager: UserManager,
){
    fun addFavorite(userId: Long, request: RequestAddFavorite): ResponseFavorite {
        val validatedUser = userValidation(userId)

        val addedFavorite = favoriteManager.addFavorite(validatedUser, request)
        // 저장후 Component 에서 결과를 받아야 함
        return ResponseFavorite.of(addedFavorite)
    }

    fun findAllFavorite(userId: Long): List<ResponseFavorite> {
        val validatedUser = userValidation(userId)

        val findAllFavorite = favoriteManager.findAllFavorite(validatedUser.id!!).map(ResponseFavorite::of)

        return findAllFavorite
    }

    fun findOneFavorite(userId: Long, favoriteId: Long): Favorite {
        val validatedUser = userValidation(userId)

        val findFavorite = favoriteManager.findOneFavorite(validatedUser.id!!, favoriteId)
        return findFavorite
    }

    fun updateFavorite(userId: Long, favoriteId: Long, request: Favorite): Favorite {
        val validatedUser = userValidation(userId)

        val updatedFavorite = favoriteManager.updateFavorite(validatedUser.id!!, favoriteId, request)
        return updatedFavorite
    }

    fun deleteOneFavorite(userId: Long, favoriteId: Long): Boolean {
        val validatedUser = userValidation(userId)

        val deleteResult = favoriteManager.deleteOneFavorite(validatedUser.id!!, favoriteId)
        return deleteResult
    }

    fun deleteAllFavorite(userId: Long): Boolean {
        val validatedUser = userValidation(userId)

        val deleteAllResult = favoriteManager.deleteAllFavorite(validatedUser.id!!)
        return deleteAllResult
    }

    fun deleteMultiFavorite(userId: Long, deleteFavoriteIds: List<Long>): Int {
        val validatedUser = userValidation(userId)

        val deleteMultiFavoriteResult = favoriteManager.deleteMultiFavorite(validatedUser.id!!, deleteFavoriteIds)
        return deleteMultiFavoriteResult
    }

    /**
     * 유저 유효성 체크
     */
    private fun userValidation(userId: Long): User =
            userManager.userValidation(userId)
}