package com.hh2.katj.domain.user.component

import com.hh2.katj.domain.user.model.User
import com.hh2.katj.domain.user.repository.UserRepository
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Transactional(readOnly = true)
class UserReader(
    private val userRepository: UserRepository
) {

    fun findByUserIdWithException(userId: Long): User {
        return userRepository.findByIdOrNull(userId)
            ?: throw NotFoundException()
    }

}
