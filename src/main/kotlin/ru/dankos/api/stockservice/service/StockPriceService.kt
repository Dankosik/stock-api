package ru.dankos.api.stockservice.service

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import org.springframework.stereotype.Service
import ru.dankos.api.stockservice.controller.dto.MoneyValue
import ru.dankos.api.stockservice.controller.dto.StockPriceResponse
import ru.dankos.api.stockservice.controller.dto.TickersListRequest
import ru.dankos.api.stockservice.extension.awaitSingle
import ru.tinkoff.piapi.contract.v1.LastPrice

@Service
class StockPriceService(
    private val cacheStockService: CacheStockService,
    private val commonStockService: CommonStockService,
) {

    suspend fun getStockPriceByTicker(ticker: String): StockPriceResponse {
        val share = commonStockService.getStockByTicker(ticker)
        val lastPrice = cacheStockService.lastPrice(share.figi).awaitSingle()[0]
        return StockPriceResponse(
            ticker = share.ticker,
            moneyValue = lastPrice.toAmount(share.currency)
        )
    }

    suspend fun getStocksPricesByTickers(request: TickersListRequest) = coroutineScope {
        request.tickers.map { async { getStockPriceByTicker(it) } }.awaitAll()
    }

    private fun LastPrice.toAmount(currency: String) = MoneyValue(
        integer = price.units.toInt(),
        fractional = price.nano,
        currency = currency
    )
}