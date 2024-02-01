package com.hh2.katj.history.model.entity

import com.hh2.katj.history.model.dto.ResponseLocationHistory
import com.hh2.katj.user.model.entity.User
import com.hh2.katj.util.model.BaseEntity
import com.hh2.katj.util.model.RoadAddress
import jakarta.persistence.*

@Entity
@Table(name = "search_location_history")
class SearchLocationHistory(
    user: User,
    roadAddress: RoadAddress,
    keyword: String,
    val faultPercentage: Int
) : BaseEntity() {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false,
        foreignKey = ForeignKey(ConstraintMode.NO_CONSTRAINT)
    )
    var user = user
        protected set

    @Embedded
    @Column(nullable = false)
    var roadAddress = roadAddress
        protected set

    @Column(nullable = false)
    var keyword = keyword
        protected set

    fun toResponseDto(): ResponseLocationHistory {
        return ResponseLocationHistory(
            userId = user.id,
            roadAddress = roadAddress,
            faultPercentage = faultPercentage
        )
    }
}