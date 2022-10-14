package br.com.sample.config

import io.github.resilience4j.common.circuitbreaker.configuration.CircuitBreakerConfigCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class Resilience4jConfig {

    /**
     * Customizing a configuration defined in the application.yml
     */
    @Bean
    fun circuitBreakerConfigCustomizer(): CircuitBreakerConfigCustomizer =
        CircuitBreakerConfigCustomizer.of("backend-a") {
//            it.slidingWindowSize(100)
        }
}
