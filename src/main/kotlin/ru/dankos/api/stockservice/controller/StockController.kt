package ru.dankos.api.stockservice.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.dankos.api.stockservice.controller.dto.StockPriceResponse
import ru.dankos.api.stockservice.controller.dto.TickersListRequest
import ru.dankos.api.stockservice.service.StockPriceService

@RestController
@RequestMapping("/stocks")
class StockController(
    private val stockPriceService: StockPriceService,
) {

//    @GetMapping("/info")
//    suspend fun getStocksInfoByTickers(@RequestBody request: TickersListRequest): List<StockInfoResponse> =
//        stockInfoService.getStocksInfoByTickers(request)
//
//    @GetMapping("/{ticker}/info")
//    suspend fun getStockInfoByTicker(@PathVariable ticker: String): StockInfoResponse =
//        stockInfoService.getStockInfoByTicker(ticker)

    @GetMapping("/price")
    suspend fun getStocksPriceByTickers(@RequestBody request: TickersListRequest): List<StockPriceResponse> =
        stockPriceService.getStocksPricesByTickers(request)

    @GetMapping("/{ticker}/price")
    suspend fun getStockPriceByTicker(@PathVariable ticker: String): StockPriceResponse =
        stockPriceService.getStockPriceByTicker(ticker)

//    @GetMapping("/{ticker}/dividends")
//    suspend fun getDividendsByTicker(
//        @PathVariable ticker: String,
//        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) date: LocalDate
//    ): List<DividendResponse> =
//        dividendStockService.getDividendsByTickerAndDate(ticker, date)
}