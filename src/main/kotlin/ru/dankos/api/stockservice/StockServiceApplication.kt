package ru.dankos.api.stockservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import reactivefeign.spring.config.EnableReactiveFeignClients
import ru.dankos.api.stockservice.config.CaffeineProperties


@EnableCaching
@EnableDiscoveryClient
@EnableReactiveFeignClients
@EnableConfigurationProperties(
    CaffeineProperties::class
)
@SpringBootApplication
class StockServiceApplication

fun main(args: Array<String>) {
    runApplication<StockServiceApplication>(*args)
}
