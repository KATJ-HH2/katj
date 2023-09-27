package com.hh2.katj.favorite.service

import com.hh2.katj.cmn.model.RoadAddress
import com.hh2.katj.favorite.component.FavoriteReader
import com.hh2.katj.favorite.model.Favorite
import com.hh2.katj.favorite.model.RequestFavorite
import org.springframework.stereotype.Service

@Service
class FavoriteService (
        private val favoriteReader: FavoriteReader
){
    fun addFavorite(userId: Long?, request: RequestFavorite): Boolean {

        val result = favoriteReader.addFavorite(userId, request)
        // 저장후 Component 에서 결과를 받아야 함
        return result
    }

    fun findAllFavorite(userId: Long?): MutableList<Favorite> {
        val findAllFavorite = favoriteReader.findAllFavorite(userId)
        return findAllFavorite
    }
}