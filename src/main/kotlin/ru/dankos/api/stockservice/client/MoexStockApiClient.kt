//package ru.dankos.api.stockservice.client
//
//import org.springframework.cloud.openfeign.FeignClient
//import org.springframework.web.bind.annotation.GetMapping
//import org.springframework.web.bind.annotation.PathVariable
//import ru.dankos.api.stockservice.client.dto.MoexStockPriceResponse
//
//@FeignClient(name = "moex-stock-api-service")
//interface MoexStockApiClient {
//
//    @GetMapping("/price/{ticker}")
//    fun getMoexStockPriceByTicker(@PathVariable ticker: String): MoexStockPriceResponse
//}