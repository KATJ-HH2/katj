package com.hh2.katj.user.component

import com.hh2.katj.user.model.User
import com.hh2.katj.user.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.lang.IllegalArgumentException

@Component
class UserReader @Autowired constructor(
        private val userRepository: UserRepository
) {
    @Transactional(readOnly = true)
    fun userValidation(userId: Long?): Boolean {
        userRepository.findByIdOrNull(userId) ?: throw IllegalArgumentException("user doesn't exist")
        return true
    }

    @Transactional(readOnly = true)
    fun findById(userId: Long): User {
        return userRepository.findByIdOrNull(userId) ?: throw IllegalArgumentException("user doesn't exist")

    }

}