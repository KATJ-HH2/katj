package com.hh2.katj.user.component

import org.springframework.stereotype.Component
import org.springframework.web.util.UriComponentsBuilder
import java.net.URI

@Component
class KakaoUriBuilder {

    private companion object {
        const val KAKAO_SEARCH_ADDRESS_URL = "https://dapi.kakao.com/v2/local/search/address.json"
        const val KAKAO_SEARCH_CAR_DIRECTION_URL = "https://apis-navi.kakaomobility.com/v1/directions"
    }

    fun buildUriByAddressSearch(address: String): URI {
        val uriBuilder = UriComponentsBuilder.fromHttpUrl(KAKAO_SEARCH_ADDRESS_URL)
        uriBuilder.queryParam("query", address)

        return uriBuilder.build().encode().toUri()
    }

    fun buildUriByCarDirectionSearch(origin: String, destination: String): URI {
        val uriBuilder = UriComponentsBuilder.fromHttpUrl(KAKAO_SEARCH_CAR_DIRECTION_URL)
        uriBuilder.queryParam("origin", origin)
        uriBuilder.queryParam("destination", destination)

        return uriBuilder.build().encode().toUri()
    }

}
