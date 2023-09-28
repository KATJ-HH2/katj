package com.hh2.katj.favorite.service

import com.hh2.katj.favorite.model.Favorite
import com.hh2.katj.favorite.model.RequestFavorite

interface FavoriteService {

    fun addFavorite(userId: Long?, request: RequestFavorite): Boolean
    fun findAllFavorite(userId: Long?): MutableList<Favorite>
    fun findOneFavorite(userId: Long?, favoriteId: Long): Favorite

}