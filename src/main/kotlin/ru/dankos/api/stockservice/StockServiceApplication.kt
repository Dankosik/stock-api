package ru.dankos.api.stockservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import ru.dankos.api.stockservice.config.CaffeineProperties
import ru.dankos.api.stockservice.config.TinkoffProperties


@EnableCaching
@SpringBootApplication
@EnableConfigurationProperties(
    TinkoffProperties::class, CaffeineProperties::class
)
class StockServiceApplication

fun main(args: Array<String>) {
    runApplication<StockServiceApplication>(*args)
}
