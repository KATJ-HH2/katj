package com.hh2.katj.favorite.model.dto

import com.hh2.katj.util.model.RoadAddress

/**
 * @author : tony
 * @description : 즐겨찾기 추가시 Controller 사용 목적의 DTO
 * @since : 2023-10-05
 */
data class RequestAddFavorite(
    var roadAddress: RoadAddress,
    var title: String,
    var description: String?,
)
