package com.hh2.katj.config

import io.github.resilience4j.retry.Retry
import io.github.resilience4j.retry.RetryConfig
import io.github.resilience4j.retry.RetryRegistry
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.retry.interceptor.RetryInterceptorBuilder.circuitBreaker
import java.util.function.Supplier


@Configuration
class RetryConfiguration {

    @Bean
    fun retryConfig(): RetryConfig {
        return RetryConfig.custom<Any>()
            .maxAttempts(2)
            .waitDuration(java.time.Duration.ofMillis(100))
//            .retryOnResult { response -> response.getStatus() === 500 }
//            .retryOnException { e -> e is WebServiceException }
//            .retryExceptions(IOException::class.java, TimeoutException::class.java)
            .build()
    }

    @Bean
    fun retryRegistry(retryConfig: RetryConfig): RetryRegistry {
        return RetryRegistry.of(retryConfig)
    }

    @Bean
    fun retry(retryRegistry: RetryRegistry): Retry {
        return Retry.of("myRetry", retryConfig())
    }

    fun <T> decorateSupplier(retry: Retry, supplier: Supplier<T>): Supplier<T> {
        return Retry.decorateSupplier(retry, supplier)
    }

}