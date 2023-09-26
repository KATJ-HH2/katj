package com.hh2.katj.favorite.component

import com.hh2.katj.cmn.model.RoadAddress
import com.hh2.katj.favorite.model.Favorite
import com.hh2.katj.favorite.repository.FavoriteRepository
import com.hh2.katj.user.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class FavoriteReader @Autowired constructor(
        private val favoriteRepository: FavoriteRepository,
        private val userRepository: UserRepository

) {
    fun addFavorite(userId: Long?, roadAddress: RoadAddress, title: String, description: String?): Boolean {
        val findUser = userRepository.findByIdOrNull(userId) ?: return false

        val favorite: Favorite = Favorite(
                user = findUser,
                roadAddress = roadAddress,
                title = title,
                description = description
        )

        // 즐겨찾기 타이틀 중복 체크
        val favoriteTitleDuplicate = favoriteRepository.findByTitle(title).isEmpty
        if (!favoriteTitleDuplicate) {
            // 중복시 오류 반환? false 반환?
            return false
        }

        favoriteRepository.save(favorite)

        return true
    }

}