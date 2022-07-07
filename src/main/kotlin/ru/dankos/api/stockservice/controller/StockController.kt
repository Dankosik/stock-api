package ru.dankos.api.stockservice.controller

import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.dankos.api.stockservice.client.MoexStockApiClient
import ru.dankos.api.stockservice.client.dto.MoexStockPriceResponse
import ru.dankos.api.stockservice.controller.dto.StockInfoResponse
import ru.dankos.api.stockservice.controller.dto.StockPriceResponse
import ru.dankos.api.stockservice.controller.dto.TickersListRequest
import ru.dankos.api.stockservice.service.StockInfoService
import ru.dankos.api.stockservice.service.StockPriceService

@RestController
@RequestMapping("/stocks")
class StockController(
    private val stockPriceService: StockPriceService,
    private val stockInfoService: StockInfoService,
    private val moexStockApiClient: MoexStockApiClient,
) {

    @GetMapping("/info")
    suspend fun getStocksInfoByTickers(@RequestBody request: TickersListRequest): List<StockInfoResponse> =
        stockInfoService.getStocksInfoByTickers(request)

    @GetMapping("/info/{ticker}")
    suspend fun getStockInfoByTicker(@PathVariable ticker: String): StockInfoResponse =
        stockInfoService.getStockInfoByTicker(ticker)

    @GetMapping("/price")
    suspend fun getStocksPriceByTickers(@RequestBody request: TickersListRequest): List<StockPriceResponse> =
        stockPriceService.getStocksPricesByTickers(request)

    @GetMapping("/price/{ticker}")
    suspend fun getStockPriceByTicker(@PathVariable ticker: String): StockPriceResponse =
        stockPriceService.getStockPriceByTicker(ticker)

    @GetMapping("/tickers")
    suspend fun getAllTickers(): List<String> = stockInfoService.getAllAvailableTickers()

    @GetMapping("/moex/{ticker}")
    suspend fun getMoexStocks(ticker: String): MoexStockPriceResponse =
        moexStockApiClient.getMoexStockPriceByTicker(ticker).awaitSingle()
}