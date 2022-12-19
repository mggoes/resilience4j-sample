package br.com.sample

import br.com.sample.client.InvoiceClient
import br.com.sample.client.OrderClient
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import java.nio.file.Files.readAllLines
import java.nio.file.Paths.get

@SpringBootApplication
@EnableFeignClients("br.com.sample.client")
class Resilience4jSampleApplication(
  private val orderClient: OrderClient,
  private val invoiceClient: InvoiceClient
) : CommandLineRunner {

  override fun run(vararg args: String?) {
    println("ORDER_ID,INVOICE_ID,SELLER_ID,ORDER_STATUS,INTERNAL_TAGS,CANCELLATION_DETAIL,INVOICE_ISSUED_DATE")
    readAllLines(get("/Users/mgoes/Downloads/file.csv"))
      .forEach { line ->
        runCatching {
          execute(line)
        }.onFailure {
          println("============>$line")
        }
      }
  }

  private fun execute(line: String) {
    val parts = line.split(",")
    val orderId = parts[0].split("_")[0]
    val invoiceId = parts[1]
    val sellerId = parts[4]

    val invoice = this.invoiceClient.retrieveInvoice(invoiceId, sellerId)
    val order = this.orderClient.retrieveOrder(orderId)

    println("$orderId,$invoiceId,$sellerId,${order.status},${order.internalTags.joinToString("/")},${order.cancelDetail?.code},${invoice.issuedDate}")
  }
}

fun main(args: Array<String>) {
  runApplication<Resilience4jSampleApplication>(*args)
}
