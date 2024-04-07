package com.hh2.katj.history.component

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.util.UriComponentsBuilder
import java.net.URI

@Component
class KakaoUriBuilder(
    @Value("\${api.kakao.url.address}") private val kakaoSearchAddressUrl: String,
    @Value("\${api.kakao.url.direction}") private val kakaoSearchCarDirectionUrl: String
) {

    fun buildUriByAddressSearch(address: String): URI {
        val uriBuilder = UriComponentsBuilder.fromHttpUrl(kakaoSearchAddressUrl)
        uriBuilder.queryParam("query", address)

        return uriBuilder.build().encode().toUri()
    }

    fun buildUriByCarDirectionSearch(origin: String, destination: String): URI {
        val uriBuilder = UriComponentsBuilder.fromHttpUrl(kakaoSearchCarDirectionUrl)
        uriBuilder.queryParam("origin", origin)
        uriBuilder.queryParam("destination", destination)

        return uriBuilder.build().encode().toUri()
    }

}
