package ru.dankos.api.stockservice.client

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import reactivefeign.spring.config.ReactiveFeignClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ru.dankos.api.stockservice.client.dto.MoexStockPriceResponse

@ReactiveFeignClient(name = "moex-stock-api-service")
interface MoexStockApiClient {

    @GetMapping("/moex-stock-api/stocks/price/{ticker}")
    fun getMoexStockPriceByTicker(@PathVariable("ticker") ticker: String): Mono<MoexStockPriceResponse>


    @GetMapping("/moex-stock-api/stocks/subscribe/{ticker}")
    fun getMoexStockPriceByTickerAsFlow(@PathVariable("ticker") ticker: String): Flux<MoexStockPriceResponse>
}