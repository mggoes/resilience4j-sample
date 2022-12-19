package br.com.sample.model

data class Order(
  val status: String,
  val internalTags: List<String>,
  val cancelDetail: CancelDetail?
)

data class CancelDetail(val code: String)
