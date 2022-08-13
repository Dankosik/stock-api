package ru.dankos.api.stockservice.service

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import org.springframework.stereotype.Service
import ru.dankos.api.stockservice.controller.dto.StockPriceResponse
import ru.dankos.api.stockservice.controller.dto.TickersListRequest
import ru.dankos.api.stockservice.model.Exchanges

@Service
class StockPriceService(
    private val moexStockService: MoexStockService,
    private val yahooStockService: YahooStockService,
    private val exchangeResolver: ExchangeResolver,
) {

    suspend fun getStockPriceByTicker(ticker: String): StockPriceResponse =
        when (exchangeResolver.resolveExchange(ticker)) {
            Exchanges.MOEX -> moexStockService.getStockPriceByTicker(ticker)
            Exchanges.NYSE -> yahooStockService.getStockPriceByTicker(ticker)
        }

    suspend fun getStocksPricesByTickers(request: TickersListRequest) = coroutineScope {
        request.tickers.map { async { getStockPriceByTicker(it) } }.awaitAll()
    }
}