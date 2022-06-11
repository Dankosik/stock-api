package ru.dankos.api.stockservice.controller

import org.springframework.web.bind.annotation.*
import ru.dankos.api.stockservice.controller.dto.SpbeStockPriceResponse
import ru.dankos.api.stockservice.controller.dto.StockInfoResponse
import ru.dankos.api.stockservice.controller.dto.TickersListRequest
import ru.dankos.api.stockservice.service.spbe.SpbeStockInfoService
import ru.dankos.api.stockservice.service.spbe.SpbeStockPriceService

@RestController
@RequestMapping("/stock-api/spbe/stocks")
class SpbeStockController(
    private val spbeStockPriceService: SpbeStockPriceService,
    private val spbeStockInfoService: SpbeStockInfoService
) {

    @GetMapping("/info")
    suspend fun getStocksInfoByTickers(@RequestBody request: TickersListRequest): List<StockInfoResponse> =
        spbeStockInfoService.getSpbeStocksInfoByTickers(request)

    @GetMapping("/info/{ticker}")
    suspend fun getStockInfoByTicker(@PathVariable ticker: String): StockInfoResponse =
        spbeStockInfoService.getSpbeStockInfoByTicker(ticker)

    @GetMapping("/price")
    suspend fun getStocksPriceByTickers(@RequestBody request: TickersListRequest): List<SpbeStockPriceResponse> =
        spbeStockPriceService.getSpbeStocksPricesByTickers(request)

    @GetMapping("/price/{ticker}")
    suspend fun getStockPriceByTicker(@PathVariable ticker: String): SpbeStockPriceResponse =
        spbeStockPriceService.getSpbeStockPriceByTicker(ticker)

    @GetMapping("/tickers")
    suspend fun getAllTickers(): List<String> = spbeStockInfoService.getAllSpbeAvailableTickers()
}