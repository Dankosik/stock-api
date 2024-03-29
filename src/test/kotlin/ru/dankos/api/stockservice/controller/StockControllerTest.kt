package ru.dankos.api.stockservice.controller

import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import ru.dankos.api.stockservice.utils.stubForAllTickersFromMoex
import ru.dankos.api.stockservice.utils.stubForAllTickersFromNyse
import ru.dankos.api.stockservice.utils.stubForStockFromMoex
import java.io.File

class StockControllerTest : BaseIntegrationTest() {

    @Test
    fun `test get moex stock by ticker`() {
        stubForStockFromMoex("SBER", "src/test/resources/__files/moex-response/sber-stock-price.json")
        stubForAllTickersFromMoex()
        stubForAllTickersFromNyse()
        webTestClient.get()
            .uri("/stocks/SBER/price")
            .exchange()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectStatus().is2xxSuccessful
            .expectBody()
            .json(File("src/test/resources/__files/expected-response/sber-stock-price.json").readText(Charsets.UTF_8))
    }
}