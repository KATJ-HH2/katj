package com.hh2.katj.domain.user.repository

import com.hh2.katj.domain.user.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long>