package ru.dankos.api.stockservice.controller

import org.springframework.web.bind.annotation.*
import ru.dankos.api.stockservice.controller.dto.MoexStockPriceResponse
import ru.dankos.api.stockservice.controller.dto.StockInfoResponse
import ru.dankos.api.stockservice.controller.dto.TickersListRequest
import ru.dankos.api.stockservice.service.moex.MoexStockInfoService
import ru.dankos.api.stockservice.service.moex.MoexStockPriceService

@RestController
@RequestMapping("/moex/stocks")
class MoexStockController(
    private val moexStockPriceService: MoexStockPriceService,
    private val moexStockInfoService: MoexStockInfoService
) {

    @GetMapping("/info")
    suspend fun getStocksInfoByTickers(@RequestBody request: TickersListRequest): List<StockInfoResponse> =
        moexStockInfoService.getMoexStocksInfoByTickers(request)

    @GetMapping("/info/{ticker}")
    suspend fun getStockInfoByTicker(@PathVariable ticker: String): StockInfoResponse =
        moexStockInfoService.getMoexStockInfoByTicker(ticker)

    @GetMapping("/price")
    suspend fun getStocksPriceByTickers(@RequestBody request: TickersListRequest): List<MoexStockPriceResponse> =
        moexStockPriceService.getMoexStocksPricesByTickers(request)

    @GetMapping("/price/{ticker}")
    suspend fun getStockPriceByTicker(@PathVariable ticker: String): MoexStockPriceResponse =
        moexStockPriceService.getMoexStockPriceByTicker(ticker)

    @GetMapping("/tickers")
    suspend fun getAllTickers(): List<String> = moexStockInfoService.getAllMoexAvailableTickers()
}