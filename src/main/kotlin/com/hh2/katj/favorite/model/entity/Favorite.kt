package com.hh2.katj.favorite.model.entity

import com.hh2.katj.favorite.model.dto.ResponseFavorite
import com.hh2.katj.user.model.entity.User
import com.hh2.katj.util.model.BaseEntity
import com.hh2.katj.util.model.RoadAddress
import jakarta.persistence.*

@Entity
@Table(name = "favorite")
class Favorite(
    roadAddress: RoadAddress,
    title: String,
    user: User,
    description: String?,
): BaseEntity() {
    @Embedded
    var roadAddress: RoadAddress = roadAddress
        protected set

    @Column(unique = true)
    var title: String = title
        protected set

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false,
        foreignKey = ForeignKey(ConstraintMode.NO_CONSTRAINT))
    var user: User = user!!
        protected set

    var description: String? = description
        protected set


    fun update(requestFavorite: Favorite) {
        this.roadAddress = requestFavorite.roadAddress
        this.title = requestFavorite.title
        this.description = requestFavorite.description
    }

    fun addFavoriteTo(user: User) {
        this.user = user
    }

    fun toResponseDto(): ResponseFavorite {
        return ResponseFavorite(
            id = this.id,
            roadAddress = this.roadAddress,
            title = this.title,
            description = this.description,
            user = this.user,
        )
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Favorite

        if (roadAddress != other.roadAddress) return false
        if (title != other.title) return false
        if (user != other.user) return false
        if (description != other.description) return false
        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        var result = roadAddress.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + user.hashCode()
        result = 31 * result + (description?.hashCode() ?: 0)
        result = 31 * result + (id?.hashCode() ?: 0)
        return result
    }
}

