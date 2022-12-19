package br.com.sample.controller

import br.com.sample.client.OrderClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/orders")
class OrderController(private val client: OrderClient) {

  @GetMapping("/{id}")
  fun retrieveOrder(@PathVariable id: String) = this.client.retrieveOrder(id)
}
