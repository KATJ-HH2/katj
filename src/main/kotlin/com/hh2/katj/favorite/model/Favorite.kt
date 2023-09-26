package com.hh2.katj.favorite.model

import com.hh2.katj.cmn.model.BaseEntity
import com.hh2.katj.cmn.model.RoadAddress
import com.hh2.katj.user.model.User
import jakarta.persistence.*
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate


@Entity
@DynamicInsert
@DynamicUpdate
class Favorite(

        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long? = null,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "user_id", nullable = false,
                foreignKey = ForeignKey(ConstraintMode.NO_CONSTRAINT))
        var user: User,

        @Embedded
        var roadAddress: RoadAddress,

        @Column(unique = true)
        var title: String,
        var description: String?

):BaseEntity() {
}