package com.hh2.katj.domain.favorite.repository

import com.hh2.katj.domain.favorite.model.Favorite
import com.hh2.katj.domain.user.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface FavoriteRepository : JpaRepository<Favorite, Long> {
    fun findFavoriteByUser(user: User): Favorite?
}