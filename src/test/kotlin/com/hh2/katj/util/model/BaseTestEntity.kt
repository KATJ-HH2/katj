package com.hh2.katj.util.model

import io.restassured.RestAssured
import org.junit.jupiter.api.BeforeEach
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.junit.jupiter.Container

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BaseTestEntity()
 {

     @LocalServerPort private val port: Int? = null
     @BeforeEach
     fun init() {
         RestAssured.port = port!!
     }

    companion object {
        @Container
        private val mySQLContainer = MySQLContainer<Nothing>("mysql:latest")
    }
}