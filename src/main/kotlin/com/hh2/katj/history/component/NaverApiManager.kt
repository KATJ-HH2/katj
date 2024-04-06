package com.hh2.katj.history.component

import com.hh2.katj.history.model.dto.NaverAddressSearchResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.*
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import java.net.URI
import java.nio.charset.StandardCharsets

@Component
class NaverApiManager(
    private val naverUriBuilder: NaverUriBuilder,
    private val restTemplate: RestTemplate,
    @Value("\${naver.api.address.clientId}") private val naverClientId: String,
    @Value("\${naver.api.address.clientSecret}") private val naverClientSecret: String
) {
    fun requestAddressSearch(address: String?): ResponseEntity<NaverAddressSearchResponse>? {
        checkNotNull(address) { "api 호출 오류" }

        val uri: URI = naverUriBuilder.buildUriByAddressSearch(address)
        val headers = HttpHeaders()
        headers.set("X-NCP-APIGW-API-KEY-ID", "$naverClientId")
        headers.set("X-NCP-APIGW-API-KEY", "$naverClientSecret")
        val httpEntity = HttpEntity<String>(headers)
        val response = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, NaverAddressSearchResponse::class.java)
        return response
    }
}