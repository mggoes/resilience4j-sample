package br.com.sample.client

import br.com.sample.model.Customer
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping

@FeignClient(name = "CustomerClient", url = "https://634989a25df9528514026037.mockapi.io")
interface CustomerClient {

    @GetMapping("/customers")
    fun customers(): List<Customer>
}
