package com.hh2.katj.favorite.repository

import com.hh2.katj.favorite.model.entity.Favorite
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface FavoriteRepository : JpaRepository<Favorite, Long> {
    fun findByTitle(title: String): Favorite?
    fun findByUserIdAndId(userId: Long?, favoriteId: Long?): Favorite?

    fun findAllByUserId(userId: Long?): MutableList<Favorite>

    @Modifying
    @Query("DELETE FROM Favorite f WHERE f.user.id = :userId")
    fun deleteAllByUserId(@Param("userId") userId: Long?)

    @Modifying
    @Query("DELETE FROM Favorite f WHERE f.user.id = :userId AND f.id IN :deleteFavoriteIds")
    fun deleteFavoritesByUserIdAndIdIn(@Param("userId") userId: Long?, @Param("deleteFavoriteIds") deleteFavoriteIds: List<Long?>): Int

}