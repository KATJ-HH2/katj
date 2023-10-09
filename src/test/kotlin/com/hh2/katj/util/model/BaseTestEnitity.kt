package com.hh2.katj.util.model

import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.junit.jupiter.Container

open class BaseTestEnitity {
    companion object {
        @Container
        private val mySQLContainer = MySQLContainer<Nothing>("mysql:latest").apply {
            withDatabaseName("katj")
            withUsername("katj")
            withPassword("katj123!")
        }

        @DynamicPropertySource
        @JvmStatic
        fun registerDynamicProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl)
            registry.add("spring.datasource.username", mySQLContainer::getUsername)
            registry.add("spring.datasource.password", mySQLContainer::getPassword)
        }
    }
}