package br.com.sample.basics

import br.com.sample.service.UserService
import io.github.resilience4j.bulkhead.Bulkhead
import io.github.resilience4j.bulkhead.ThreadPoolBulkhead
import io.github.resilience4j.circuitbreaker.CircuitBreaker
import io.github.resilience4j.decorators.Decorators
import io.github.resilience4j.retry.Retry
import io.github.resilience4j.timelimiter.TimeLimiter
import java.time.Duration
import java.util.concurrent.Executors

/*fun main() {
    decoratos()
    circuitBreaker()
    bulkhead()
}*/

/**
 * Bulkhead
 */
fun bulkhead() {
    val cb = CircuitBreaker.ofDefaults("someService")
    val threadPoolBulkhead = ThreadPoolBulkhead.ofDefaults("backendService")
    val executorService = Executors.newScheduledThreadPool(3)
    val timeLimiter = TimeLimiter.of(Duration.ofSeconds(1))

    val future = Decorators.ofSupplier { UserService().longRunningOperation() }
        .withThreadPoolBulkhead(threadPoolBulkhead)
        .withTimeLimiter(timeLimiter, executorService)
        .withCircuitBreaker(cb)
        .withFallback { it -> println("Recovering from $it") }
        .get()
        .toCompletableFuture()

    println(future.get())
}

/**
 * Circuit Breaker
 */
fun circuitBreaker() {
    val cb = CircuitBreaker.ofDefaults("someService")
    val result = cb.executeSupplier { UserService().dangerousOperation() }
    println(result)
}

/**
 * Decorators
 */
fun decoratos() {
    val cb = CircuitBreaker.ofDefaults("someService")
    // 3 retry attempts and a fixed time interval between retries of 500ms by default
    val retry = Retry.ofDefaults("someService")
    val bulkhead = Bulkhead.ofDefaults("backendService")

    val decoratedSupplier = Decorators.ofSupplier { UserService().dangerousOperation() }
        .withCircuitBreaker(cb)
        .withBulkhead(bulkhead)
        .withRetry(retry)
        .decorate()

    val anotherDecoratedSupplier =
        Decorators.ofSupplier { UserService().anotherDangerousOperation() }
            .withCircuitBreaker(cb)
            .withBulkhead(bulkhead)
            .withRetry(retry)
            .decorate()

    runCatching {
        println(decoratedSupplier.get())
        println(anotherDecoratedSupplier.get())
    }.onFailure {
        println("Error during supplier call... $it")
    }
}

