package com.hh2.katj.history.component

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.util.UriComponentsBuilder
import java.net.URI

@Component
class NaverUriBuilder(
    @Value("\${api.naver.url.address}") private val naverSearchAddressUrl: String,
) {
    fun buildUriByAddressSearch(address: String): URI {
        val uriBuilder = UriComponentsBuilder.fromHttpUrl(naverSearchAddressUrl)
        uriBuilder.queryParam("query", address)

        return uriBuilder.build().encode().toUri()
    }
}
