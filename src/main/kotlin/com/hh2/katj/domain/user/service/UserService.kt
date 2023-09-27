package com.hh2.katj.domain.user.service

import com.hh2.katj.domain.favorite.component.FavoriteSpec
import com.hh2.katj.domain.user.component.UserReader
import com.hh2.katj.domain.user.component.UserSpec
import com.hh2.katj.domain.favorite.model.Favorite
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userSpec: UserSpec,
    private val userReader: UserReader,
    private val favoriteSpec: FavoriteSpec
) {

    fun addFavoritePlace(userId: Long, favorite: Favorite): Long {
        val user = userReader.findByUserIdWithException(userId)

        userSpec.addFavoriteToUser(user, favorite)
        favoriteSpec.saveFavorite(favorite)

        return favorite.id
    }

}
