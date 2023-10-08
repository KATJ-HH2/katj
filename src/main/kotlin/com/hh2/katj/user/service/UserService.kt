package com.hh2.katj.user.service

import com.hh2.katj.user.component.UserManager
import com.hh2.katj.user.model.entity.User
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userManager: UserManager
) {

    fun findByUserId(userId: Long): User {

        val findUser = userManager.findByUserId(userId)

        return findUser
    }

}