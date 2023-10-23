package com.hh2.katj.user.controller

import com.hh2.katj.user.model.request.RequestCreateUser
import com.hh2.katj.user.model.request.ResponseUser
import com.hh2.katj.user.service.UserService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/user")
@RestController
class UserController (
    private val userService: UserService,
){

    /**
     * 해당 사용자에 즐겨찾기 추가
     */
    @PostMapping
    fun addFavorite(@Valid @RequestBody requestCreateUser: RequestCreateUser): ResponseEntity<ResponseUser> {
        val responseUser = userService.createUser(requestCreateUser.toEntity())
        return ResponseEntity.ok(responseUser)
    }

}
