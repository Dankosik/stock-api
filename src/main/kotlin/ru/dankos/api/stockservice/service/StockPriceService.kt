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
import kotlin.math.pow

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

    private fun LastPrice.toAmount(currency: String): MoneyValue {
        val amountExtraZeros = (10.0.pow((countZeroDigits(price.nano)).toDouble())).toInt()
        val exchangeUnit = (price.nano / amountExtraZeros)
        val minorUnits = 10.0.pow(exchangeUnit.toString().chars().count().toDouble()).toInt()
        return if (price.nano.toString().count() == AMOUNT_DIGITS_EXCHANGE_UNIT) {
            MoneyValue(
                value = ((price.units * minorUnits + exchangeUnit)).toInt(),
                minorUnits = minorUnits * 10,
                currency = currency
            )
        } else {
            MoneyValue(
                value = (price.units * minorUnits + exchangeUnit).toInt(),
                minorUnits = minorUnits,
                currency = currency
            )
        }
    }

    private fun countZeroDigits(number: Int) =
        number.toString().reversed().chars().takeWhile { it.toChar() == ASCII_ZERO_CODE }.count()

    companion object {
        private const val ASCII_ZERO_CODE = '0'
        private const val AMOUNT_DIGITS_EXCHANGE_UNIT = 8
    }
}