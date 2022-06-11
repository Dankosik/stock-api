package ru.dankos.api.stockservice.service

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import org.springframework.stereotype.Service
import ru.dankos.api.stockservice.controller.dto.MoneyValue
import ru.dankos.api.stockservice.controller.dto.StockPriceResponse
import ru.dankos.api.stockservice.controller.dto.TickersListRequest
import ru.dankos.api.stockservice.exception.StockNotFoundException
import ru.dankos.api.stockservice.extension.awaitSingle
import ru.tinkoff.piapi.contract.v1.LastPrice
import ru.tinkoff.piapi.core.InstrumentsService
import ru.tinkoff.piapi.core.MarketDataService
import ru.tinkoff.piapi.core.exception.ApiRuntimeException

@Service
class StockPriceService(
    private val instrumentsService: InstrumentsService,
    private val marketDataService: MarketDataService
) {
    suspend fun getStockPriceByTicker(ticker: String): StockPriceResponse =
        try {
            val share = instrumentsService.getShareByTicker(ticker, "SPBXM").awaitSingle()
            val lastPrice = marketDataService.getLastPrices(listOf(share.figi)).awaitSingle()[0]
            StockPriceResponse(
                ticker = share.ticker,
                moneyValue = lastPrice.toAmount(share.currency)
            )
        } catch (e: ApiRuntimeException) {
            throw StockNotFoundException("Stock not found")
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