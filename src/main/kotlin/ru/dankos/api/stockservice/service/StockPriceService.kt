package ru.dankos.api.stockservice.service

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.stereotype.Service
import ru.dankos.api.stockservice.client.MoexStockApiClient
import ru.dankos.api.stockservice.client.YahooStockApiClient
import ru.dankos.api.stockservice.controller.dto.StockPriceResponse
import ru.dankos.api.stockservice.controller.dto.TickersListRequest
import ru.dankos.api.stockservice.model.Exchanges

@Service
class StockPriceService(
    private val yahooStockApiClient: YahooStockApiClient,
    private val moexApiClient: MoexStockApiClient,
    private val exchangeResolver: ExchangeResolver,
) {

    suspend fun getStockPriceByTicker(ticker: String): StockPriceResponse =
        when (exchangeResolver.resolveExchange(ticker)) {
            Exchanges.MOEX -> moexApiClient.getStockPriceByTickerFromMoex(ticker).awaitSingle()
            Exchanges.NYSE -> yahooStockApiClient.getStockPriceByTickerFromNyse(ticker).awaitSingle()
        }

    suspend fun getStocksPricesByTickers(request: TickersListRequest) = coroutineScope {
        request.tickers.map { async { getStockPriceByTicker(it) } }.awaitAll()
    }
}