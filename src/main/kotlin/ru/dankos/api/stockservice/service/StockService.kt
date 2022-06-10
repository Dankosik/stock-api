package ru.dankos.api.stockservice.service

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import org.springframework.stereotype.Service
import ru.dankos.api.stockservice.controller.dto.MoneyValue
import ru.dankos.api.stockservice.controller.dto.StockResponse
import ru.dankos.api.stockservice.controller.dto.TickersListRequest
import ru.dankos.api.stockservice.exception.StockNotFoundException
import ru.dankos.api.stockservice.extension.awaitSingle
import ru.tinkoff.piapi.contract.v1.LastPrice
import ru.tinkoff.piapi.core.InstrumentsService
import ru.tinkoff.piapi.core.MarketDataService
import ru.tinkoff.piapi.core.exception.ApiRuntimeException

@Service
class StockService(
    private val instrumentsService: InstrumentsService,
    private val marketDataService: MarketDataService
) {
    suspend fun getStockByTicker(ticker: String): StockResponse =
        try {
            val stock = instrumentsService.getShareByTicker(ticker, "SPBXM").awaitSingle()
            val figi = stock.figi
            val lastPrice = marketDataService.getLastPrices(listOf(figi)).awaitSingle()[0]
            StockResponse(
                ticker = stock.ticker,
                name = stock.name,
                moneyValue = lastPrice.toAmount(stock.currency)
            )
        } catch (e: ApiRuntimeException) {
            throw StockNotFoundException("Stock not found")
        }

    suspend fun getStocksByTickers(request: TickersListRequest) = coroutineScope {
        request.tickers.map { async { getStockByTicker(it) } }.awaitAll()
    }

    private fun LastPrice.toAmount(currency: String) = MoneyValue(
        integer = price.units.toInt(),
        fractional = price.nano,
        currency = currency
    )
}