package br.com.sample.client

import br.com.sample.config.FeignClientConfig
import br.com.sample.model.Order
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient(
  name = "OrderClient",
  url = "http://localhost",
  configuration = [FeignClientConfig::class]
)
interface OrderClient {

  @GetMapping("/orders/{id}?caller.scopes=admin")
  fun retrieveOrder(
    @PathVariable id: String
  ): Order
}
