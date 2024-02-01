package com.hh2.katj.history.service

import com.hh2.katj.history.component.KakaoApiManager
import com.hh2.katj.util.annotation.KATJTestContainerE2E
import com.hh2.katj.util.model.BaseTestEntity
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

@KATJTestContainerE2E
class KakaoApiManagerTest(
    private val kakaoApiManager: KakaoApiManager,
): BaseTestEntity() {

    @Test
    fun `주소가 null 이면 null 반환`() {
        // given
        val address = null

        // when
        val result = kakaoApiManager.requestAddressSearch(address)

        // then
        assertThat(result).isNull()
    }

    @Test
    fun `주소가 유효한 경우 검색결과 1개 이상 반환`() {
        // given
        val address = "서울시 관악구 법원단지5가길 76"

        // when
        val result = kakaoApiManager.requestAddressSearch(address)?.body

        // then
        assertThat(result!!.meta.totalCount).isGreaterThanOrEqualTo(1)
        assertThat(result.documents[0].roadAddress).isNotNull
    }

    @Test
    fun `주소가 유효하지 않은 경우 카운트가 0, 빈 리스트 반환`() {
        // given
        val address = "서울시 관악구 법원단지5가길 7655555"

        // when
        val result = kakaoApiManager.requestAddressSearch(address)?.body

        // then
        assertThat(result!!.meta.totalCount).isEqualTo(0)
        assertThat(result.documents.size).isEqualTo(0)
    }

}