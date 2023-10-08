package com.hh2.katj.user.service

import com.hh2.katj.user.component.KakaoUriBuilder
import com.hh2.katj.user.model.entity.KakaoAddressSearchResponse
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import java.net.URI

@Service
class KakaoApiService(
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

    fun requestCarDirectionSearch(origin: String?, destination: String?): KakaoAddressSearchResponse? {
        // TODO 형식 검사
        // ${X좌표},${Y좌표},name=${출발지명} 또는 ${X좌표},${Y좌표}
        checkNotNull(origin)
        checkNotNull(destination)

        val uri = kakaoUriBuilder.buildUriByCarDirectionSearch(origin, destination)
        val headers = HttpHeaders()
        headers.set(HttpHeaders.AUTHORIZATION, "KakaoAK $kakaoRestApiKey")

        val httpEntity = HttpEntity<String>(headers)

        return restTemplate.exchange(uri, HttpMethod.GET, httpEntity, KakaoAddressSearchResponse::class.java).body
    }

}
