package ru.dankos.api.stockservice.service

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.stereotype.Service
import reactor.kotlin.core.publisher.toMono
import ru.dankos.api.stockservice.client.MoexStockApiClient
import ru.dankos.api.stockservice.controller.dto.StockPriceRequest
import ru.dankos.api.stockservice.controller.dto.StockPriceResponse
import ru.dankos.api.stockservice.controller.dto.TickersListRequest

@Service
class MoexStockService(
    private val moexStockApiClient: MoexStockApiClient
) {

    suspend fun getStockPriceByTicker(ticker: String): StockPriceResponse =
        moexStockApiClient.getMoexStockPriceByTicker(ticker).awaitSingle()

    suspend fun getStocksPricesByTickers(request: TickersListRequest) = coroutineScope {
        request.tickers.map { async { getStockPriceByTicker(it) } }.awaitAll()
    }

    suspend fun subscribePrice(stockPriceRequest: StockPriceRequest): StockPriceResponse =
        moexStockApiClient.getMoexStockPriceByTickerAsFlow(stockPriceRequest.ticker)
            .log()
            .filter { it.moneyValue < stockPriceRequest.moneyValue }
            .toMono()
            .awaitSingle()
}