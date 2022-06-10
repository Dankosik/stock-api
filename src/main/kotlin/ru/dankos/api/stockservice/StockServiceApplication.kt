package ru.dankos.api.stockservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import ru.dankos.api.stockservice.config.TinkoffProperties


@SpringBootApplication
@EnableConfigurationProperties(
    TinkoffProperties::class
)
class StockServiceApplication

fun main(args: Array<String>) {
    runApplication<StockServiceApplication>(*args)
}
