package com.hh2.katj.domain.favorite.service

import com.hh2.katj.domain.favorite.component.FavoriteReader
import com.hh2.katj.domain.favorite.component.FavoriteSpec
import com.hh2.katj.domain.favorite.model.Favorite
import com.hh2.katj.domain.user.component.UserReader
import com.hh2.katj.domain.user.model.User
import org.springframework.stereotype.Service

@Service
class FavoriteService(
    private val favoriteReader: FavoriteReader,
    private val favoriteSpec: FavoriteSpec,
    private val userReader: UserReader,
) {

    fun findFavoriteByUserId(userId: Long): Favorite {
        val user = userReader.findByUserIdWithException(userId)
        return favoriteReader.findByUserWithException(user)
    }

}
