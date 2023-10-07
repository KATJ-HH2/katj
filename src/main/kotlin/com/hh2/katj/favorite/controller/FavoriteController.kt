package com.hh2.katj.favorite.controller

import com.hh2.katj.favorite.model.dto.RequestAddFavorite
import com.hh2.katj.favorite.model.dto.ResponseFavorite
import com.hh2.katj.favorite.model.entity.Favorite
import com.hh2.katj.favorite.service.FavoriteService
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
){

    @PostMapping
    fun addFavorite(@Valid @RequestBody requestAddFavorite: RequestAddFavorite): ResponseEntity<ResponseFavorite> {
        val userId = requestAddFavorite.user.id!!

        val responseFavorite = favoriteService.addFavorite(userId, Favorite.toEntity(requestAddFavorite))

        return ResponseEntity.ok(responseFavorite)
    }

}