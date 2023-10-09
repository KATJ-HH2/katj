package com.hh2.katj.domain.favorite.component

import com.hh2.katj.domain.favorite.model.Favorite
import com.hh2.katj.domain.user.model.User
import com.hh2.katj.domain.favorite.repository.FavoriteRepository
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Transactional(readOnly = true)
class FavoriteReader(
    private val favoriteRepository: FavoriteRepository,
) {

    fun findByUserWithException(user: User): Favorite {
        return favoriteRepository.findFavoriteByUser(user) ?: throw NotFoundException()
    }

    fun findUserByFavorite(favorite: Favorite): User {
        return favorite.user
    }

}
