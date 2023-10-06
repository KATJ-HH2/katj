package com.hh2.katj.user.component

import com.hh2.katj.user.model.entity.User
import com.hh2.katj.user.repository.UserRepository
import com.hh2.katj.util.exception.ExceptionMessage
import com.hh2.katj.util.exception.findByIdOrThrowMessage
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class UserManager(
        private val userRepository: UserRepository
) {

    @Transactional(readOnly = true)
    fun userValidation(userId: Long): User =
            userRepository.findByIdOrThrowMessage(userId, ExceptionMessage.USER_DOES_NOT_EXIST.name)

}