package ru.dankos.api.stockservice.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import ru.dankos.api.stockservice.controller.dto.StockInfoResponse
import ru.dankos.api.stockservice.controller.dto.StockPriceResponse
import ru.dankos.api.stockservice.controller.dto.TickersListRequest
import ru.dankos.api.stockservice.service.StockInfoService
import ru.dankos.api.stockservice.service.StockPriceService

@RestController
class StockController(
    private val stockPriceService: StockPriceService,
    private val stockInfoService: StockInfoService
) {

    @GetMapping("/stocks/info/{ticker}")
    suspend fun getStockInfoByTicker(@PathVariable ticker: String): StockInfoResponse =
        stockInfoService.getStockInfoByTicker(ticker)

    @GetMapping("/stocks/price/{ticker}")
    suspend fun getStockPriceByTicker(@PathVariable ticker: String): StockPriceResponse =
        stockPriceService.getStockPriceByTicker(ticker)

    @GetMapping("/stocks/info")
    suspend fun getStocksInfoByTickers(@RequestBody request: TickersListRequest): List<StockInfoResponse> =
        stockInfoService.getStocksInfoByTickers(request)

    @GetMapping("/stocks/price")
    suspend fun getStocksPriceByTickers(@RequestBody request: TickersListRequest): List<StockPriceResponse> =
        stockPriceService.getStocksPricesByTickers(request)
}