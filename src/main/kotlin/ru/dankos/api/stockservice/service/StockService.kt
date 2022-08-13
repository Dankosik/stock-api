package ru.dankos.api.stockservice.service

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import org.springframework.stereotype.Service
import ru.dankos.api.stockservice.controller.dto.StocksBaseInfoResponse
import ru.dankos.api.stockservice.controller.dto.StocksPriceResponse
import ru.dankos.api.stockservice.controller.dto.TickersListRequest
import ru.dankos.api.stockservice.model.Exchanges

@Service
class StockService(
    private val moexStockService: MoexStockService,
    private val yahooStockService: YahooStockService,
    private val exchangeResolver: ExchangeResolver,
) {

    suspend fun getStockPriceByTicker(ticker: String): StocksPriceResponse = coroutineScope {
        when (exchangeResolver.resolveExchange(ticker)) {
            Exchanges.MOEX -> StocksPriceResponse(listOf(moexStockService.getStockPriceByTicker(ticker)))
            Exchanges.NYSE, Exchanges.NASDAQ -> StocksPriceResponse(
                listOf(yahooStockService.getStockPriceByTicker(ticker))
            )
            Exchanges.COMMON -> StocksPriceResponse(
                listOf(
                    async { yahooStockService.getStockPriceByTicker(ticker) },
                    async { moexStockService.getStockPriceByTicker(ticker) }
                ).awaitAll()
            )
        }
    }

    suspend fun getStockBaseInfoByTicker(ticker: String): StocksBaseInfoResponse = coroutineScope {
        when (exchangeResolver.resolveExchange(ticker)) {
            Exchanges.MOEX -> StocksBaseInfoResponse(listOf(moexStockService.getStockBaseInfoByTicker(ticker)))
            Exchanges.NYSE, Exchanges.NASDAQ -> StocksBaseInfoResponse(
                listOf(yahooStockService.getStockBaseInfoByTicker(ticker))
            )
            Exchanges.COMMON -> StocksBaseInfoResponse(
                listOf(
                    async { yahooStockService.getStockBaseInfoByTicker(ticker) },
                    async { moexStockService.getStockBaseInfoByTicker(ticker) }
                ).awaitAll()
            )
        }
    }

    suspend fun getStocksPricesByTickers(request: TickersListRequest) = coroutineScope {
        request.tickers.map { async { getStockPriceByTicker(it) } }.awaitAll()
    }
}