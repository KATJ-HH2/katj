package com.hh2.katj.favorite.controller

import com.hh2.katj.favorite.model.dto.request.RequestAddFavorite
import com.hh2.katj.favorite.model.dto.request.RequestUpdateFavorite
import com.hh2.katj.favorite.model.dto.response.ResponseFavorite
import com.hh2.katj.favorite.service.FavoriteService
import com.hh2.katj.user.service.UserService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping("/favorite")
@RestController
class FavoriteController (
    private val favoriteService: FavoriteService,
    private val userService: UserService,
){

    /**
     * 해당 사용자에 즐겨찾기 추가
     */
    @PostMapping
    fun addFavorite(@RequestParam userId: Long, @Valid @RequestBody requestAddFavorite: RequestAddFavorite): ResponseEntity<ResponseFavorite> {
        val findUser = userService.findByUserId(userId)

        val responseFavorite = favoriteService.addFavorite(requestAddFavorite.toEntity(findUser))

        return ResponseEntity.ok(responseFavorite)
    }


    /**
     * 해당 사용자의 모든 즐겨찾기 조회
     */
    @GetMapping
    fun findAllFavorite(@RequestParam userId: Long): ResponseEntity<List<ResponseFavorite>>{
        val responseFavoriteList = favoriteService.findAllFavorite(userId)
        return ResponseEntity.ok(responseFavoriteList)
    }
    /**
     * 해당 사용자의 해당 즐겨찾기 조회
     */
    @GetMapping("/{favoriteId}")
    fun findOneFavorite(@RequestParam userId: Long, @PathVariable("favoriteId") favoriteId: Long): ResponseEntity<ResponseFavorite> {
        val findOneFavorite = favoriteService.findOneFavorite(userId, favoriteId)
        return ResponseEntity.ok(findOneFavorite)
    }

    /**
     * 해당 사용자의 해당 즐겨찾기 수정
     */
    @PutMapping("/{favoriteId}")
    fun updateFavorite(@RequestParam userId: Long, @PathVariable("favoriteId") favoriteId: Long, @RequestBody requestUpdateFavorite: RequestUpdateFavorite): ResponseEntity<ResponseFavorite> {
        val findUser = userService.findByUserId(userId)

        val updateFavorite = favoriteService.updateFavorite(favoriteId, requestUpdateFavorite.toEntity(findUser))
        return ResponseEntity.ok(updateFavorite)
    }

    /**
     * 해당 사용자의 해당 즐겨찾기 삭제
     */
    @DeleteMapping("/{favoriteId}")
    fun deleteOneFavorite(@RequestParam userId: Long, @PathVariable("favoriteId") favoriteId: Long): ResponseEntity<Boolean> {
        val deleteOneFavorite = favoriteService.deleteOneFavorite(userId, favoriteId)
        return ResponseEntity.ok(deleteOneFavorite)
    }

    /**
     * 해당 사용자의 요청온 모든 즐겨찾기 삭제
     */
    @DeleteMapping("/delete-multi")
    fun deleteMultiFavorite(@RequestParam userId: Long, @RequestBody deleteFavoriteIds: List<Long>): ResponseEntity<Boolean> {
        val deleteOneFavorite = favoriteService.deleteMultiFavorite(userId, deleteFavoriteIds)
        return ResponseEntity.ok(deleteOneFavorite)
    }

    /**
     * 해당 사용자의 모든 즐겨찾기 삭제
     */
    @DeleteMapping("/delete-all")
    fun deleteAllFavorite(@RequestParam userId: Long): ResponseEntity<Boolean> {
        val deleteOneFavorite = favoriteService.deleteAllFavorite(userId)
        return ResponseEntity.ok(deleteOneFavorite)
    }
}
