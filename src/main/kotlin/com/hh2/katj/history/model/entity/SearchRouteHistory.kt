package com.hh2.katj.history.model.entity

import com.hh2.katj.trip.model.DepartureRoadAddress
import com.hh2.katj.trip.model.DestinationRoadAddress
import com.hh2.katj.user.model.entity.User
import com.hh2.katj.util.model.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "search_route_history")
class SearchRouteHistory(
    user: User,
    departureRoadAddress: DepartureRoadAddress,
    destinationRoadAddress: DestinationRoadAddress,
) : BaseEntity() {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false,
        foreignKey = ForeignKey(ConstraintMode.NO_CONSTRAINT)
    )
    var user = user
        protected set

    @Embedded
    @Column(nullable = false)
    var departureRoadAddress = departureRoadAddress
        protected set

    @Embedded
    @Column(nullable = false)
    var destinationRoadAddress = destinationRoadAddress
        protected set
}