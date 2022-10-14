package br.com.sample

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication
@EnableFeignClients("br.com.sample.client")
class Resilience4jSampleApplication

fun main(args: Array<String>) {
    runApplication<Resilience4jSampleApplication>(*args)
}
