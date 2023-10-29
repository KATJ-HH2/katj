package com.hh2.katj.taxidriver.controller

import com.hh2.katj.util.annotation.KATJTestContainerE2E
import com.hh2.katj.util.model.BaseTestEntity
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@KATJTestContainerE2E
class TaxiDriverAcceptanceTest(): BaseTestEntity() {

    @BeforeEach
    fun setUp() {

    }

    /**
     * given: 새로운 택시를 생성하고
     * when:  드라이버가 배정되고, 드라이버를 조회하면
     * then: 새로운 택시를 조회할 수 있다.
     */
    @DisplayName("드라이버 생성")
    @Test
    fun createTaxiDriver() {

    }
}