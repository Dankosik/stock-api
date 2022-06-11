package ru.dankos.api.stockservice.service.moex

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
class MoexStockInfoService(
    private val instrumentsService: InstrumentsService,
    private val tinkoffProperties: TinkoffProperties,
) {

    suspend fun getMoexStockInfoByTicker(ticker: String): StockInfoResponse = try {
        instrumentsService.getShareByTicker(ticker, tinkoffProperties.api.moex.classCode)
            .awaitSingle().toStockInfoResponse()
    } catch (e: ApiRuntimeException) {
        throw StockNotFoundException("Stock not found in Moscow exchange")
    }

    suspend fun getAllMoexAvailableTickers(): List<String> =
        instrumentsService.allShares.awaitSingle().map { it.ticker }

    suspend fun getMoexStocksInfoByTickers(request: TickersListRequest) = coroutineScope {
        request.tickers.map { async { getMoexStockInfoByTicker(it) } }.awaitAll()
    }

    private fun Share.toStockInfoResponse() = StockInfoResponse(
        ticker = ticker,
        companyName = name
    )
}