package com.hh2.katj.user.component

import com.hh2.katj.favorite.model.entity.Favorite
import com.hh2.katj.user.model.entity.User
import com.hh2.katj.user.repository.UserRepository
import com.hh2.katj.util.exception.ExceptionMessage.ID_DOES_NOT_EXIST
import com.hh2.katj.util.exception.ExceptionMessage.USER_DOES_NOT_EXIST
import com.hh2.katj.util.exception.findByIdOrThrowMessage
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class UserManager(
        private val userRepository: UserRepository
) {

    @Transactional(readOnly = true)
    fun userValidation(userId: Long): User =
            userRepository.findByIdOrThrowMessage(userId, USER_DOES_NOT_EXIST.name)

    @Transactional
    fun addFavoriteToUser(user: User, favorite: Favorite) {
        favorite.addFavoriteTo(user)
    }

    @Transactional(readOnly = true)
    fun findByUserId(userId: Long): User {
        val findUser = userRepository.findByIdOrThrowMessage(userId, ID_DOES_NOT_EXIST.name)
        return findUser
    }

}