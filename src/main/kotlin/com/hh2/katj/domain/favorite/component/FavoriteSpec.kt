package com.hh2.katj.domain.favorite.component

import com.hh2.katj.domain.favorite.model.Favorite
import com.hh2.katj.domain.favorite.repository.FavoriteRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Transactional
class FavoriteSpec(
    private val favoriteRepository: FavoriteRepository
) {

    fun saveFavorite(favorite: Favorite) {
        favoriteRepository.save(favorite)
    }

}
