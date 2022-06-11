package ru.dankos.api.stockservice.service.moex

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import org.springframework.stereotype.Service
import ru.dankos.api.stockservice.config.TinkoffProperties
import ru.dankos.api.stockservice.controller.dto.MoexMoneyValue
import ru.dankos.api.stockservice.controller.dto.MoexStockPriceResponse
import ru.dankos.api.stockservice.controller.dto.TickersListRequest
import ru.dankos.api.stockservice.exception.StockNotFoundException
import ru.dankos.api.stockservice.extension.awaitSingle
import ru.tinkoff.piapi.contract.v1.LastPrice
import ru.tinkoff.piapi.core.InstrumentsService
import ru.tinkoff.piapi.core.MarketDataService
import ru.tinkoff.piapi.core.exception.ApiRuntimeException

@Service
class MoexStockPriceService(
    private val instrumentsService: InstrumentsService,
    private val marketDataService: MarketDataService,
    private val tinkoffProperties: TinkoffProperties,
) {

    suspend fun getMoexStockPriceByTicker(ticker: String): MoexStockPriceResponse =
        try {
            val share = instrumentsService.getShareByTicker(ticker, tinkoffProperties.api.moex.classCode).awaitSingle()
            val lastPrice = marketDataService.getLastPrices(listOf(share.figi)).awaitSingle()[0]
            MoexStockPriceResponse(
                ticker = share.ticker,
                moneyValue = lastPrice.toAmount(share.currency)
            )
        } catch (e: ApiRuntimeException) {
            throw StockNotFoundException("Stock not found in Moscow exchange")
        }

    suspend fun getMoexStocksPricesByTickers(request: TickersListRequest) = coroutineScope {
        request.tickers.map { async { getMoexStockPriceByTicker(it) } }.awaitAll()
    }

    private fun LastPrice.toAmount(currency: String) = MoexMoneyValue(
        integer = price.units.toInt(),
        fractional = price.nano,
        currency = currency
    )
}