package br.com.sample.service

import br.com.sample.model.Product
import br.com.sample.model.User
import io.github.resilience4j.bulkhead.annotation.Bulkhead
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import io.github.resilience4j.retry.annotation.Retry
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit.SECONDS

@Service
class UserService {

    fun dangerousOperation() = "Crossing the street!"

    fun anotherDangerousOperation() {
        println("Cleaning the house...")
        throw IllegalStateException("House is too dirty!")
    }

    fun longRunningOperation() {
        println("Washing the dishes!")
        SECONDS.sleep(2)
    }

    @Bulkhead(name = "backend-a")
    fun retrieveAll(): List<User> {
        SECONDS.sleep(5)
        return listOf(User("Matheus", 29))
    }

    @Retry(name = "backend-a")
    fun retrieve(id: String): List<User> {
        println("Calling retrieveUsers2")
        throw IllegalStateException("Test $id")
    }

    @CircuitBreaker(name = "backend-b", fallbackMethod = "retrieveProductsFallback")
//    @RateLimiter(name = "backend-b")
//    @Bulkhead(name = "backend-b", fallbackMethod = "retrieveUserProductsFallback")
//    @Retry(name = "backend-b")
//    @TimeLimiter(name = "backend-b")
    fun retrieveProducts(): List<Product> {
        println("Calling retrieveUserProducts")
        throw IllegalStateException("Test")
    }

    private fun retrieveProductsFallback(ex: IllegalStateException): List<Product> {
        println("Recovering from $ex")
        return listOf(Product("Laptop"))
    }
}
