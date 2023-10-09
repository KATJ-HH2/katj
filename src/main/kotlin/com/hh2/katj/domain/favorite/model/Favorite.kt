package com.hh2.katj.domain.favorite.model

import com.hh2.katj.common.BaseEntity
import com.hh2.katj.domain.user.model.User
import jakarta.persistence.*

@Entity
@Table(name = "favorite")
class Favorite(
    user: User,
    title: String,
    description: String,
) : BaseEntity() {
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", foreignKey = ForeignKey(ConstraintMode.NO_CONSTRAINT))
    var user: User = user
        protected set

    @Column(nullable = false)
    var title: String = title
        protected set

    @Column(nullable = false)
    var description: String = description
        protected set

    fun addFavoriteTo(user: User) {
        this.user = user
    }
}