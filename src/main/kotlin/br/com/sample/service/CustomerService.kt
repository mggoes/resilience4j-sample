package br.com.sample.service

import br.com.sample.client.CustomerClient
import org.springframework.stereotype.Service

@Service
class CustomerService(private val customerClient: CustomerClient) {

    fun retrieveAll() = this.customerClient.customers()
}
