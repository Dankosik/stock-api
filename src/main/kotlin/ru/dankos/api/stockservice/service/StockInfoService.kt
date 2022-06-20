package ru.dankos.api.stockservice.service

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import org.springframework.stereotype.Service
import ru.dankos.api.stockservice.controller.dto.StockInfoResponse
import ru.dankos.api.stockservice.controller.dto.TickersListRequest
import ru.dankos.api.stockservice.extension.awaitSingle
import ru.tinkoff.piapi.contract.v1.Share

@Service
class StockInfoService(
    private val cacheStockService: CacheStockService,
    private val commonStockService: CommonStockService,
) {

    suspend fun getStockInfoByTicker(ticker: String): StockInfoResponse =
        commonStockService.getStockByTicker(ticker).toStockInfoResponse()

    suspend fun getAllAvailableTickers(): List<String> =
        cacheStockService.getAllStocks().awaitSingle().map { it.ticker }


    suspend fun getStocksInfoByTickers(request: TickersListRequest) = coroutineScope {
        request.tickers.map { async { getStockInfoByTicker(it) } }.awaitAll()
    }

    private fun Share.toStockInfoResponse() = StockInfoResponse(
        ticker = ticker,
        stockName = name
    )
}