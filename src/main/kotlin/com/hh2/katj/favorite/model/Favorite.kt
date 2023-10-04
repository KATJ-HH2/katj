package com.hh2.katj.favorite.model

import com.hh2.katj.util.model.BaseEntity
import com.hh2.katj.util.model.RoadAddress
import com.hh2.katj.user.model.entity.User
import jakarta.persistence.*
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate


@Entity
@DynamicInsert
@DynamicUpdate
class Favorite(

        @Embedded
        var roadAddress: RoadAddress,

        @Column(unique = true)
        var title: String,

        var description: String?,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "user_id", nullable = false,
                foreignKey = ForeignKey(ConstraintMode.NO_CONSTRAINT))
        var user: User,

        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long? = null

): BaseEntity() {
}