package br.com.sample.config

import com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES
import com.fasterxml.jackson.databind.DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL
import com.fasterxml.jackson.databind.MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SNAKE_CASE
import com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS
import com.fasterxml.jackson.databind.SerializationFeature.WRITE_ENUMS_USING_TO_STRING
import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import feign.Logger.Level.FULL
import feign.codec.Decoder
import org.springframework.beans.factory.ObjectFactory
import org.springframework.beans.factory.ObjectProvider
import org.springframework.boot.autoconfigure.http.HttpMessageConverters
import org.springframework.cloud.openfeign.support.HttpMessageConverterCustomizer
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder
import org.springframework.cloud.openfeign.support.SpringDecoder
import org.springframework.context.annotation.Bean
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter


class FeignClientConfig {

  @Bean
  fun logger() = FULL

  @Bean
  fun feignDecoder(customizers: ObjectProvider<HttpMessageConverterCustomizer>): Decoder {
    val converter = MappingJackson2HttpMessageConverter(this.customMapper())
    val objectFactory = ObjectFactory<HttpMessageConverters> { HttpMessageConverters(converter) }
    return ResponseEntityDecoder(SpringDecoder(objectFactory, customizers))
  }

  private fun customMapper() = JsonMapper.builder()
    .disable(WRITE_DATES_AS_TIMESTAMPS)
    .disable(FAIL_ON_UNKNOWN_PROPERTIES)
    .propertyNamingStrategy(SNAKE_CASE)
    .enable(ACCEPT_CASE_INSENSITIVE_ENUMS)
    .enable(WRITE_ENUMS_USING_TO_STRING)
    .enable(READ_UNKNOWN_ENUM_VALUES_AS_NULL)
    .addModules(
      JavaTimeModule(), Jdk8Module(),
      KotlinModule.Builder()
        .withReflectionCacheSize(512)
        .configure(KotlinFeature.NullToEmptyCollection, false)
        .configure(KotlinFeature.NullToEmptyMap, false)
        .configure(KotlinFeature.NullIsSameAsDefault, true)
        .configure(KotlinFeature.SingletonSupport, false)
        .configure(KotlinFeature.StrictNullChecks, false)
        .build()
    )
    .build()
}
