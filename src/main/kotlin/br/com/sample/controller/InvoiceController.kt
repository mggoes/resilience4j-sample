package br.com.sample.controller

import br.com.sample.client.InvoiceClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/invoices")
class InvoiceController(private val client: InvoiceClient) {

  @GetMapping("/{id}")
  fun retrieveInvoice(@PathVariable id: String, @RequestParam sellerId: String) =
    this.client.retrieveInvoice(id, sellerId)
}
