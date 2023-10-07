import com.hh2.katj.trip.component.TripReader
import org.springframework.stereotype.Service

@Service
class DriverTripService (
    private val tripReader: TripReader,
//    private val tripSpec: TripSpec,
) {
    fun getTripInfo(tripId: Long): List<Any> {
        val trip = tripReader.findByTripIdWithException(tripId)
        val departure = trip.departure
        val destination = trip.destination
        val fare = trip.fare
//        return listOf(departure, destination, fare)
        return listOf("왕십리", "선릉", 10000)
    }
}

//) {
//    fun getTripInfo(tripId: Long): List<Any> {
//        val trip = tripReader.findByTripIdWithException(tripId)
//        val departure = trip.departure
//        val destination = trip.destination
//        val fare = trip.fare
//        val tripInfo = listOf<Any>(departure, destination, fare)
//        return tripInfo
//    }
//}