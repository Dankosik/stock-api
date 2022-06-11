package ru.dankos.api.stockservice.service.spbe

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import org.springframework.stereotype.Service
import ru.dankos.api.stockservice.config.TinkoffProperties
import ru.dankos.api.stockservice.controller.dto.SpbeMoneyValue
import ru.dankos.api.stockservice.controller.dto.SpbeStockPriceResponse
import ru.dankos.api.stockservice.controller.dto.TickersListRequest
import ru.dankos.api.stockservice.exception.StockNotFoundException
import ru.dankos.api.stockservice.extension.awaitSingle
import ru.tinkoff.piapi.contract.v1.LastPrice
import ru.tinkoff.piapi.core.InstrumentsService
import ru.tinkoff.piapi.core.MarketDataService
import ru.tinkoff.piapi.core.exception.ApiRuntimeException

@Service
class SpbeStockPriceService(
    private val instrumentsService: InstrumentsService,
    private val marketDataService: MarketDataService,
    private val tinkoffProperties: TinkoffProperties,
) {

    suspend fun getSpbeStockPriceByTicker(ticker: String): SpbeStockPriceResponse =
        try {
            val share = instrumentsService.getShareByTicker(ticker, tinkoffProperties.api.spbe.classCode).awaitSingle()
            val lastPrice = marketDataService.getLastPrices(listOf(share.figi)).awaitSingle()[0]
            SpbeStockPriceResponse(
                ticker = share.ticker,
                moneyValue = lastPrice.toAmount(share.currency)
            )
        } catch (e: ApiRuntimeException) {
            throw StockNotFoundException("Stock not found")
        }

    suspend fun getSpbeStocksPricesByTickers(request: TickersListRequest) = coroutineScope {
        request.tickers.map { async { getSpbeStockPriceByTicker(it) } }.awaitAll()
    }

    private fun LastPrice.toAmount(currency: String) = SpbeMoneyValue(
        value = ((price.nano.div(10000000)) + (price.units) * 100).toInt(),
        minorUnits = tinkoffProperties.minorUnitsByCurrency[currency] ?: tinkoffProperties.defaultMinorUnits,
        currency = currency,
    )
}