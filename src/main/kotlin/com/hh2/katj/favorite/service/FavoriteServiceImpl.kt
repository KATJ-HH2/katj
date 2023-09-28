package com.hh2.katj.favorite.service

import com.hh2.katj.favorite.component.FavoriteReader
import com.hh2.katj.favorite.model.Favorite
import com.hh2.katj.favorite.model.RequestFavorite
import com.hh2.katj.user.component.UserReader
import org.springframework.stereotype.Service


@Service
class FavoriteServiceImpl (
        private val favoriteReader: FavoriteReader,
        private val userReader: UserReader
): FavoriteService{
    override fun addFavorite(userId: Long?, request: RequestFavorite): Boolean {

        userValidation(userId)

        // userValidation 진행 후 userId 검증이 이루어지기 때문에 파라미터르 !!로 단정 지음
        val result = favoriteReader.addFavorite(userId!!, request)
        // 저장후 Component 에서 결과를 받아야 함

        return result
    }

    override fun findAllFavorite(userId: Long?): MutableList<Favorite> {

        userValidation(userId)

        val findAllFavorite = favoriteReader.findAllFavoriteByUserId(userId)
        return findAllFavorite
    }

    override fun findOneFavorite(userId: Long?, favoriteId: Long): Favorite {

        userValidation(userId)

        val findFavorite = favoriteReader.findByUserIdAndFavoriteId(userId!!, favoriteId)

         return findFavorite
    }

    /**
     * 유저 유효성 체크
     */
    private fun userValidation(userId: Long?): Boolean {
        return userReader.userValidation(userId)
    }
}