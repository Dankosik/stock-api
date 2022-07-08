package ru.dankos.api.stockservice.service

import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.stereotype.Service
import reactor.kotlin.core.publisher.toMono
import ru.dankos.api.stockservice.client.MoexStockApiClient
import ru.dankos.api.stockservice.client.dto.MoexStockPriceResponse
import ru.dankos.api.stockservice.controller.dto.StockPriceRequest

@Service
class MoexStockService(
    private val moexStockApiClient: MoexStockApiClient
) {

    suspend fun subscribePrice(stockPriceRequest: StockPriceRequest): MoexStockPriceResponse =
        moexStockApiClient.getMoexStockPriceByTickerAsFlow(stockPriceRequest.ticker)
            .log()
            .filter { it.moneyValue < stockPriceRequest.moneyValue }
            .toMono()
            .awaitSingle()
}