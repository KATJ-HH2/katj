package com.hh2.katj.favorite.model.entity

import com.hh2.katj.util.model.BaseEntity
import com.hh2.katj.util.model.RoadAddress
import com.hh2.katj.user.model.entity.User
import jakarta.persistence.*
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate


@Entity
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
        var user: User = user
                protected set

        var description: String? = description
                protected set

        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long? = null

        fun updateRoadAddress(newRoadAddress: RoadAddress) {
                this.roadAddress = newRoadAddress
        }
}