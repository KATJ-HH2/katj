package com.hh2.katj.favorite.model.dto

import com.hh2.katj.user.model.entity.User
import com.hh2.katj.util.model.RoadAddress

/**
 * @author : tony
 * @description : 즐겨찾기 조회 후 결과 반환시 Controller 사용 목적의 DTO
 * @since : 2023-10-06
 */
data class ResponseFavorite(
    val id: Long?,
    var roadAddress: RoadAddress,
    var title: String,
    var description: String?,
    var user: User,
)
