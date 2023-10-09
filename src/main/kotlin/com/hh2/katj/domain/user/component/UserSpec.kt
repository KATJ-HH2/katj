package com.hh2.katj.domain.user.component

import com.hh2.katj.domain.favorite.model.Favorite
import com.hh2.katj.domain.user.model.User
import com.hh2.katj.domain.user.repository.UserRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Transactional
class UserSpec(
    private val userRepository: UserRepository
) {

    fun saveUser(user: User): User {
        return userRepository.save(user)
    }

    fun addFavoriteToUser(user: User, favorite: Favorite) {
        favorite.addFavoriteTo(user)
    }
}
