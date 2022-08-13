package ru.dankos.api.stockservice.service

import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.stereotype.Service
import ru.dankos.api.stockservice.client.YahooStockApiClient
import ru.dankos.api.stockservice.controller.dto.StockBaseInfoResponse
import ru.dankos.api.stockservice.controller.dto.StockPriceResponse

@Service
class YahooStockService(
    private val yahooStockApiClient: YahooStockApiClient,
) {

    suspend fun getStockPriceByTicker(ticker: String): StockPriceResponse =
        yahooStockApiClient.getStockPriceByTickerFromNyse(ticker).awaitSingle()

    suspend fun getStockBaseInfoByTicker(ticker: String): StockBaseInfoResponse =
        yahooStockApiClient.getYahooStockBaseInfoByTicker(ticker).awaitSingle()
}