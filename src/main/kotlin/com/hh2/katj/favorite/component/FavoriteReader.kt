package com.hh2.katj.favorite.component

import com.hh2.katj.favorite.model.Favorite
import com.hh2.katj.favorite.model.RequestFavorite
import com.hh2.katj.favorite.repository.FavoriteRepository
import com.hh2.katj.user.component.UserReader
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.lang.IllegalArgumentException

@Component
@Transactional(readOnly = true)
class FavoriteReader @Autowired constructor(
        private val favoriteRepository: FavoriteRepository,
        private val userReader: UserReader
) {

    @Transactional
    fun addFavorite(userId: Long, request: RequestFavorite): Boolean {
        // user 찾기 실패시 오류 반환 또는 false 반환 정책 정해야 할듯
        // validation 과 findById를 분리 하여 Reader 쪽에서 @Transactional 을 묶음
        val findUser = userReader.findById(userId)

        // 즐겨찾기 타이틀 중복 체크
        val favoriteTitleDuplicate = favoriteRepository.findByTitle(request.title).isEmpty

        if (!favoriteTitleDuplicate) {
            // 중복시 오류 반환? false 반환?
            return false
        }

        val favorite = Favorite(
                user = findUser,
                roadAddress = request.roadAddress,
                title = request.title,
                description = request.description
        )

        favoriteRepository.save(favorite)

        return true
    }

    fun findAllFavoriteByUserId(userId: Long?): MutableList<Favorite> {
        val findAllFavorite = favoriteRepository.findAllByUserId(userId)

        return findAllFavorite
    }

    fun findByUserIdAndFavoriteId(userId: Long, favoriteId: Long): Favorite {
        val findFavorite = favoriteRepository.findByUserIdAndId(userId, favoriteId)

        return findFavorite.orElseThrow { throw IllegalArgumentException("can not find matched favorite") }
    }


}