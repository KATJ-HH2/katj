package com.hh2.katj.favorite.service

import com.hh2.katj.favorite.component.FavoriteReader
import com.hh2.katj.favorite.model.entity.Favorite
import com.hh2.katj.favorite.model.dto.RequestAddFavorite
import com.hh2.katj.util.model.RoadAddress
import org.springframework.stereotype.Service

@Service
class FavoriteService (
        private val favoriteReader: FavoriteReader,
){
    fun addFavorite(userId: Long?, request: RequestAddFavorite): Favorite {

        val addedFavorite = favoriteReader.addFavorite(userId, request)
        // 저장후 Component 에서 결과를 받아야 함
        return addedFavorite
    }

    fun findAllFavorite(userId: Long?): MutableList<Favorite> {
        val findAllFavorite = favoriteReader.findAllFavorite(userId)
        return findAllFavorite
    }

    fun findOneFavorite(userId: Long?, favoriteId: Long?): Favorite {
        val findFavorite = favoriteReader.findOneFavorite(userId, favoriteId)
        return findFavorite
    }

    fun updateFavorite(userId: Long?, favoriteId: Long?, request: Favorite): Favorite {
        val updatedFavorite = favoriteReader.updateFavorite(userId, favoriteId, request)
        return updatedFavorite
    }
}