package ru.dankos.api.stockservice.service

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.stereotype.Service
import ru.dankos.api.stockservice.client.YahooStockApiClient
import ru.dankos.api.stockservice.controller.dto.StockPriceResponse
import ru.dankos.api.stockservice.controller.dto.TickersListRequest

@Service
class YahooStockPriceService(
    private val yahooStockApiClient: YahooStockApiClient,
) {

    suspend fun getStockPriceByTicker(ticker: String): StockPriceResponse =
        yahooStockApiClient.getUsStockPriceByTicker(ticker).awaitSingle()

    suspend fun getStocksPricesByTickers(request: TickersListRequest) = coroutineScope {
        request.tickers.map { async { getStockPriceByTicker(it) } }.awaitAll()
    }
}