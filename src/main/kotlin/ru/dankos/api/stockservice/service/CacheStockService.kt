package ru.dankos.api.stockservice.service

import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import ru.tinkoff.piapi.contract.v1.LastPrice
import ru.tinkoff.piapi.contract.v1.Share
import ru.tinkoff.piapi.core.InstrumentsService
import ru.tinkoff.piapi.core.MarketDataService
import java.util.concurrent.CompletableFuture


@Service
class CacheStockService(
    private val instrumentsService: InstrumentsService,
    private val marketDataService: MarketDataService,
) {

    @Cacheable(value = ["allShares"])
    fun getAllStocks(): CompletableFuture<List<Share>> = instrumentsService.allShares

    @Cacheable(value = ["figis"], key = "#figi")
    fun lastPrice(figi: String): CompletableFuture<List<LastPrice>> =
        marketDataService.getLastPrices(listOf(figi))
}