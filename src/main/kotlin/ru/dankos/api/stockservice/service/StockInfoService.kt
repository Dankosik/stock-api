package ru.dankos.api.stockservice.service

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import org.springframework.stereotype.Service
import ru.dankos.api.stockservice.controller.dto.StockInfoResponse
import ru.dankos.api.stockservice.controller.dto.TickersListRequest
import ru.dankos.api.stockservice.extension.awaitSingle
import ru.tinkoff.piapi.contract.v1.Share
import ru.tinkoff.piapi.core.InstrumentsService

@Service
class StockInfoService(
    private val instrumentsService: InstrumentsService,
) {

    suspend fun getStockInfoByTicker(ticker: String): StockInfoResponse =
        instrumentsService.getShareByTicker(ticker, "SPBXM").awaitSingle().toStockInfoResponse()

    suspend fun getAllAvailableTickers(): List<String> =
        instrumentsService.allShares.awaitSingle().map { it.ticker }

    suspend fun getStocksInfoByTickers(request: TickersListRequest) = coroutineScope {
        request.tickers.map { async { getStockInfoByTicker(it) } }.awaitAll()
    }

    private fun Share.toStockInfoResponse() = StockInfoResponse(
        ticker = ticker,
        companyName = name
    )
}