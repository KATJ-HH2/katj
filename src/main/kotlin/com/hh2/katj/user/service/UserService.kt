package com.hh2.katj.user.service

import com.hh2.katj.user.component.UserManager
import com.hh2.katj.user.model.entity.User
import com.hh2.katj.user.model.entity.UserStatus
import com.hh2.katj.util.exception.ExceptionMessage
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class UserService(
    private val userManager: UserManager
) {

    fun findByUserId(userId: Long): User {
        val findUser = userManager.findByUserId(userId)
        return findUser
    }


    /**
     * 유저 유효성 확인
     */
    fun userValidationCheck(userId: Long): User {
        val validatedUser = userManager.userValidation(userId)
        userStatusActiveCheck(validatedUser)
        return validatedUser
    }

    /**
     * 해당 사용자의 상태가 ACTIVE인지 확인
     */
    internal fun userStatusActiveCheck(user: User) {
        if (user.status != UserStatus.ACTIVE) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ExceptionMessage.INCORRECT_STATUS_VALUE.name)
        }
    }

}