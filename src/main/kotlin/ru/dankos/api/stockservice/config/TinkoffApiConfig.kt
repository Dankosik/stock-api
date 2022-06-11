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
}