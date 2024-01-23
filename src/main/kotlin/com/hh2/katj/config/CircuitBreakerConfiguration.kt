package com.hh2.katj.config

import io.github.resilience4j.circuitbreaker.CircuitBreaker
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CircuitBreakerConfiguration {
    @Bean
    fun circuitBreakerConfig(): CircuitBreakerConfig {
        return CircuitBreakerConfig.custom()
            .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.COUNT_BASED)
            .slidingWindowSize(10)
            .failureRateThreshold(30.0F)
            .minimumNumberOfCalls(3)
            .waitDurationInOpenState(java.time.Duration.ofSeconds(1))
            .permittedNumberOfCallsInHalfOpenState(3)
            .automaticTransitionFromOpenToHalfOpenEnabled(true)
            .build()
    }

    @Bean
    fun circuitBreakerRegistry(circuitBreakerConfig: CircuitBreakerConfig): CircuitBreakerRegistry {
        return CircuitBreakerRegistry.of(circuitBreakerConfig)
    }
    @Bean
    fun circuitBreaker(circuitBreakerRegistry: CircuitBreakerRegistry): CircuitBreaker {
        return CircuitBreaker.of("myCircuitBreaker", circuitBreakerConfig())
    }
}
