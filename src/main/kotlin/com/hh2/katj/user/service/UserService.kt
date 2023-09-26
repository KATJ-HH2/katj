package com.hh2.katj.user.service

import com.hh2.katj.user.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(
        private val userRepository: UserRepository
) {
}