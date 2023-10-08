package com.hh2.katj.favorite.model.dto

import com.hh2.katj.favorite.model.entity.Favorite
import com.hh2.katj.user.model.entity.User
import com.hh2.katj.util.model.RoadAddress

/**
 * @author : tony
 * @description : 즐겨찾기 수정시 Controller 사용 목적의 DTO
 * @since : 2023-10-05
 */
data class RequestUpdateFavorite(
    val id: Long?,
    var roadAddress: RoadAddress,
    var title: String,
    var description: String?,
) {
    fun toEntity(user: User): Favorite {
        return Favorite(
            roadAddress = this.roadAddress,
            title = this.title,
            description = this.description,
            user = user,
        )
    }
}
