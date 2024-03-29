package ru.dankos.api.stockservice.client

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import reactivefeign.spring.config.ReactiveFeignClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ru.dankos.api.stockservice.client.dto.AllTickersResponse
import ru.dankos.api.stockservice.controller.dto.StockBaseInfoResponse
import ru.dankos.api.stockservice.controller.dto.StockPriceResponse

@ReactiveFeignClient(name = "moex-stock-api-service", url = "\${feign-services.moex-stock-api-endpoint}")
interface MoexStockApiClient {

    @GetMapping("/moex-stock-api/stocks/{ticker}/price")
    fun getStockPriceByTickerFromMoex(@PathVariable("ticker") ticker: String): Mono<StockPriceResponse>

    @GetMapping("/moex-stock-api/stocks/{ticker}/subscribe")
    fun getMoexStockPriceByTickerAsFlow(@PathVariable("ticker") ticker: String): Flux<StockPriceResponse>

    @GetMapping("/moex-stock-api/stocks/tickers")
    fun getAllAvailableTickers(): Mono<AllTickersResponse>

    @GetMapping("/moex-stock-api/stocks/{ticker}/baseInfo")
    fun getMoexStockBaseInfoByTicker(@PathVariable("ticker") ticker: String): Mono<StockBaseInfoResponse>
}