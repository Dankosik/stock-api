package ru.dankos.api.stockservice.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import ru.dankos.api.stockservice.controller.dto.StockResponse
import ru.dankos.api.stockservice.controller.dto.TickersListRequest
import ru.dankos.api.stockservice.service.StockService

@RestController
class StockController(
    private val stockService: StockService
) {

    @GetMapping("/stocks/{ticker}")
    suspend fun getStockByTicker(@PathVariable ticker: String): StockResponse? =
        stockService.getStockByTicker(ticker)

    @GetMapping("/stocks")
    suspend fun getStocksByTickers(@RequestBody request: TickersListRequest): List<StockResponse> =
        stockService.getStocksByTickers(request)
}