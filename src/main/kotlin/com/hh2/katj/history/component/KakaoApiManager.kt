package com.hh2.katj.history.component

import com.hh2.katj.history.model.dto.KakaoAddressSearchResponse
import com.hh2.katj.history.model.dto.KakaoRouteSearchResponse
import com.hh2.katj.trip.model.DepartureRoadAddress
import com.hh2.katj.trip.model.DestinationRoadAddress
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import java.net.URI

@Component
class KakaoApiManager(
    private val kakaoUriBuilder: KakaoUriBuilder,
    private val restTemplate: RestTemplate,
) {

    private val kakaoRestApiKey: String = "e53e7a4c2825be5f0c4c487a91ae3e2b"

    fun requestAddressSearch(address: String?): KakaoAddressSearchResponse? {
        checkNotNull(address) { return null }

        val uri: URI = kakaoUriBuilder.buildUriByAddressSearch(address)
        val headers = HttpHeaders()
        headers.set(HttpHeaders.AUTHORIZATION, "KakaoAK $kakaoRestApiKey")

        val httpEntity = HttpEntity<String>(headers)

        val response = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, KakaoAddressSearchResponse::class.java).body
        
        return response
    }

    fun requestCarDirectionSearch(departureRoadAddress: DepartureRoadAddress,
                                  destinationRoadAddress: DestinationRoadAddress): KakaoRouteSearchResponse? {
        val origin = "${departureRoadAddress.departureLongitude},${departureRoadAddress.departureLatitude}"
        val destination = "${destinationRoadAddress.destinationLongitude},${destinationRoadAddress.destinationLatitude}"

        val uri = kakaoUriBuilder.buildUriByCarDirectionSearch(origin, destination)
        val headers = HttpHeaders()
        headers.set(HttpHeaders.AUTHORIZATION, "KakaoAK $kakaoRestApiKey")

        val httpEntity = HttpEntity<String>(headers)

        return restTemplate.exchange(uri, HttpMethod.GET, httpEntity, KakaoRouteSearchResponse::class.java).body
    }

}
