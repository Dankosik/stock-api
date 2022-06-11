package ru.dankos.api.stockservice.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.tinkoff.piapi.core.InstrumentsService
import ru.tinkoff.piapi.core.InvestApi
import ru.tinkoff.piapi.core.MarketDataService

@Configuration
class TinkoffApiConfig(
    private val tinkoffProperties: TinkoffProperties
) {

    @Bean
    fun instrumentsService(): InstrumentsService =
        InvestApi.createReadonly(tinkoffProperties.api.token).instrumentsService

    @Bean
    fun marketDataService(): MarketDataService =
        InvestApi.createReadonly(tinkoffProperties.api.token).marketDataService

    val currencyMap = hashMapOf<String, Int>(
        "usd" to 2, "eur" to 2, "gbp" to 2, "chf" to 2,
        "cny" to 2, "jpy" to 0, "hkd" to 2, "try" to 2, "kzt" to 2, "byn" to 2
    )
}