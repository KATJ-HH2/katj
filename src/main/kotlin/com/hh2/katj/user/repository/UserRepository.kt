package com.hh2.katj.user.repository

import com.hh2.katj.user.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository:JpaRepository<User, Long> {
}