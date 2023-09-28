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

        override fun equals(other: Any?): Boolean {
                if (this === other) return true
                if (javaClass != other?.javaClass) return false

                other as Favorite

                if (id != other.id) return false
                if (user != other.user) return false
                if (roadAddress != other.roadAddress) return false
                if (title != other.title) return false
                if (description != other.description) return false

                return true
        }

        override fun hashCode(): Int {
                var result = id?.hashCode() ?: 0
                result = 31 * result + user.hashCode()
                result = 31 * result + roadAddress.hashCode()
                result = 31 * result + title.hashCode()
                result = 31 * result + (description?.hashCode() ?: 0)
                return result
        }

        override fun toString(): String {
                return "Favorite(id=$id, user=$user, roadAddress=$roadAddress, title='$title', description=$description)"
        }


}