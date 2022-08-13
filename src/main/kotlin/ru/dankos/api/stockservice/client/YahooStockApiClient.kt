package ru.dankos.api.stockservice.client

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import reactivefeign.spring.config.ReactiveFeignClient
import reactor.core.publisher.Mono
import ru.dankos.api.stockservice.client.dto.AllTickersResponse
import ru.dankos.api.stockservice.controller.dto.StockPriceResponse

@ReactiveFeignClient(name = "yahoo-stock-api-service", url = "\${feign-services.yahoo-stock-api-endpoint}")
interface YahooStockApiClient {

    @GetMapping("/yahoo-stock-api/stocks/{ticker}/price")
    fun getStockPriceByTickerFromNyse(@PathVariable("ticker") ticker: String): Mono<StockPriceResponse>

    @GetMapping("/yahoo-stock-api/stocks/tickers")
    fun getAllAvailableTickers(): Mono<AllTickersResponse>
}