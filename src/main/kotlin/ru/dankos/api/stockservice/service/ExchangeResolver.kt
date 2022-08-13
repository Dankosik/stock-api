package ru.dankos.api.stockservice.service

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import ru.dankos.api.stockservice.client.MoexStockApiClient
import ru.dankos.api.stockservice.client.YahooStockApiClient
import ru.dankos.api.stockservice.model.Exchanges

@Service
class ExchangeResolver(
    private val yahooStockApiClient: YahooStockApiClient,
    private val moexApiClient: MoexStockApiClient
) {

    @Cacheable("exchanges")
    suspend fun resolveExchange(ticker: String): Exchanges = coroutineScope {
        val nyseTickers = async { yahooStockApiClient.getAllAvailableTickers().awaitSingle().tickers }
        val moexTickers = async { moexApiClient.getAllAvailableTickers().awaitSingle().tickers }
        val a = ticker in nyseTickers.await()
        val b = moexTickers.await().contains(ticker) && nyseTickers.await().contains(ticker)
        return@coroutineScope when {
            ticker in nyseTickers.await() && ticker in moexTickers.await() -> Exchanges.COMMON
            ticker in nyseTickers.await() -> Exchanges.NYSE
            ticker in moexTickers.await() -> Exchanges.MOEX
            else -> throw RuntimeException()
        }
    }
}