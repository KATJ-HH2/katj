package com.hh2.katj.util.model

import io.restassured.RestAssured
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.junit.jupiter.Container

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BaseTestEntity()
 {

     @LocalServerPort private val port: Int? = null
     @BeforeAll
     fun init() {
         RestAssured.port = port!!
     }

    companion object {
        @Container
        private val mySQLContainer = MySQLContainer<Nothing>("mysql:latest")
    }
}