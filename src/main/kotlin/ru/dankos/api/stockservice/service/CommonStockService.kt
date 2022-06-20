package ru.dankos.api.stockservice.service

import kotlinx.coroutines.coroutineScope
import org.springframework.stereotype.Service
import ru.dankos.api.stockservice.exception.StockNotFoundException
import ru.dankos.api.stockservice.extension.awaitSingle
import ru.tinkoff.piapi.contract.v1.Share

@Service
class CommonStockService(
    private val cacheStockService: CacheStockService,
) {

    suspend fun getStockByTicker(ticker: String): Share = coroutineScope {
        cacheStockService.getAllStocks().awaitSingle().firstOrNull { it.ticker == ticker }
            ?: throw StockNotFoundException("Stock not found")
    }
}