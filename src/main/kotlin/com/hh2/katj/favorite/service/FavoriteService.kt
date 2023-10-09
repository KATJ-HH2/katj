package com.hh2.katj.favorite.service

import com.hh2.katj.favorite.component.FavoriteManager
import com.hh2.katj.favorite.component.FavoriteReader
import com.hh2.katj.favorite.model.dto.response.ResponseFavorite
import com.hh2.katj.favorite.model.entity.Favorite
import com.hh2.katj.user.component.UserManager
import com.hh2.katj.user.service.UserService
import org.springframework.stereotype.Service

/**
 * Controller Layer 에서 Service 호출 시 User 에 대한 검증이
 * 이미 끝났다는 가정 하에 Service에서는 유저의 DB 유효성만 체크하게 되어있음
 */
@Service
class FavoriteService (
    private val favoriteManager: FavoriteManager,
    private val favoriteReader: FavoriteReader,
    private val userService: UserService,
    private val userManager: UserManager,
){
    fun addFavorite(request: Favorite): ResponseFavorite {
        val validatedUser = userService.userValidationCheck(request.user.id)
        userService.userStatusActiveCheck(validatedUser)

        userManager.addFavoriteToUser(request.user, request)

        val addedFavorite = favoriteManager.addFavorite(request)
        // 저장후 Component 에서 결과를 받아야 함

        return addedFavorite.toResponseDto()
    }

    fun findAllFavorite(userId: Long): List<ResponseFavorite> {
        val validatedUser = userService.userValidationCheck(userId)
        userService.userStatusActiveCheck(validatedUser)

        val findAllFavorite = favoriteReader.findAllFavorite(validatedUser.id).map(Favorite::toResponseDto)

        return findAllFavorite
    }

    fun findOneFavorite(userId: Long, favoriteId: Long): ResponseFavorite {
        val validatedUser = userService.userValidationCheck(userId)
        userService.userStatusActiveCheck(validatedUser)

        val findFavorite = favoriteReader.findOneFavorite(validatedUser.id, favoriteId)
        return findFavorite.toResponseDto()
    }

    fun updateFavorite(favoriteId: Long, request: Favorite): ResponseFavorite {
        val validatedUser = userService.userValidationCheck(request.user.id)
        userService.userStatusActiveCheck(validatedUser)

        val updatedFavorite = favoriteManager.updateFavorite(favoriteId, request)
        return updatedFavorite.toResponseDto()
    }

    fun deleteOneFavorite(userId: Long, favoriteId: Long): Boolean {
        val validatedUser = userService.userValidationCheck(userId)
        userService.userStatusActiveCheck(validatedUser)

        val deleteResult = favoriteManager.deleteOneFavorite(validatedUser.id, favoriteId)
        return deleteResult
    }

    fun deleteAllFavorite(userId: Long): Boolean {
        val validatedUser = userService.userValidationCheck(userId)
        userService.userStatusActiveCheck(validatedUser)

        val deleteAllResult = favoriteManager.deleteAllFavorite(validatedUser.id)
        return deleteAllResult
    }

    fun deleteMultiFavorite(userId: Long, deleteFavoriteIds: List<Long>): Boolean {
        val validatedUser = userService.userValidationCheck(userId)
        userService.userStatusActiveCheck(validatedUser)

        val deleteMultiFavoriteResult = favoriteManager.deleteMultiFavorite(validatedUser.id, deleteFavoriteIds)
        return deleteMultiFavoriteResult
    }

}