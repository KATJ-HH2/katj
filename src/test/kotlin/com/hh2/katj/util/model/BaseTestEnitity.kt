package com.hh2.katj.util.model

import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.junit.jupiter.Container

open class BaseTestEnitity {
    companion object {
        @Container
        private val mySQLContainer = MySQLContainer<Nothing>("mysql:latest")
    }
}