package ru.dankos.api.stockservice.service.spbe

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import org.springframework.stereotype.Service
import ru.dankos.api.stockservice.config.TinkoffProperties
import ru.dankos.api.stockservice.controller.dto.StockInfoResponse
import ru.dankos.api.stockservice.controller.dto.TickersListRequest
import ru.dankos.api.stockservice.exception.StockNotFoundException
import ru.dankos.api.stockservice.extension.awaitSingle
import ru.tinkoff.piapi.contract.v1.Share
import ru.tinkoff.piapi.core.InstrumentsService
import ru.tinkoff.piapi.core.exception.ApiRuntimeException

@Service
class SpbeStockInfoService(
    private val instrumentsService: InstrumentsService,
    private val tinkoffProperties: TinkoffProperties,
) {

    suspend fun getSpbeStockInfoByTicker(ticker: String): StockInfoResponse = try {
        instrumentsService.getShareByTicker(ticker, tinkoffProperties.api.spbe.classCode)
            .awaitSingle().toStockInfoResponse()
    } catch (e: ApiRuntimeException) {
        throw StockNotFoundException("Stock not found")
    }

    suspend fun getAllSpbeAvailableTickers(): List<String> =
        instrumentsService.allShares.awaitSingle().map { it.ticker }

    suspend fun getSpbeStocksInfoByTickers(request: TickersListRequest) = coroutineScope {
        request.tickers.map { async { getSpbeStockInfoByTicker(it) } }.awaitAll()
    }

    private fun Share.toStockInfoResponse() = StockInfoResponse(
        ticker = ticker,
        companyName = name
    )
}