package com.hh2.katj.favorite.controller

import com.hh2.katj.favorite.model.dto.RequestAddFavorite
import com.hh2.katj.favorite.model.dto.ResponseFavorite
import com.hh2.katj.favorite.service.FavoriteService
import com.hh2.katj.user.service.UserService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/favorite")
@RestController
class FavoriteController (
    private val favoriteService: FavoriteService,
    private val userService: UserService,
){

    @PostMapping
    fun addFavorite(@Valid @RequestBody requestAddFavorite: RequestAddFavorite, userId: Long): ResponseEntity<ResponseFavorite> {

        val findUser = userService.findByUserId(userId)
        val responseFavorite = favoriteService.addFavorite(requestAddFavorite.toEntity(findUser))

        return ResponseEntity.ok(responseFavorite)
    }

}