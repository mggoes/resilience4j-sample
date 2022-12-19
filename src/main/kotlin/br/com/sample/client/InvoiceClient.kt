package br.com.sample.client

import br.com.sample.config.FeignClientConfig
import br.com.sample.model.Invoice
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(
  name = "InvoiceClient",
  url = "http://localhost",
  configuration = [FeignClientConfig::class]
)
interface InvoiceClient {

  @GetMapping("/users/{sellerId}/invoices/{id}")
  fun retrieveInvoice(
    @PathVariable id: String,
    @PathVariable @RequestParam("caller.id") sellerId: String,
  ): Invoice
}
