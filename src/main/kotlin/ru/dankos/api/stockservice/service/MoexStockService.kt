package ru.dankos.api.stockservice.service

import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.stereotype.Service
import ru.dankos.api.stockservice.client.MoexStockApiClient
import ru.dankos.api.stockservice.controller.dto.StockPriceResponse

@Service
class MoexStockService(
    private val moexStockApiClient: MoexStockApiClient,
) {

    suspend fun getStockPriceByTicker(ticker: String): StockPriceResponse =
        moexStockApiClient.getStockPriceByTickerFromMoex(ticker).awaitSingle()
}