package ru.dankos.api.stockservice.utils

import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.client.WireMock.stubFor
import java.io.File

fun stubForStockFromMoex(ticker: String, bodyFilePath: String) {
    stubFor(
        WireMock.get(WireMock.urlEqualTo("/moex-stock-api/stocks/$ticker/price"))
            .willReturn(
                WireMock.aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "application/json;")
                    .withBody(File(bodyFilePath).readText(Charsets.UTF_8))
            )
    )
}

fun stubForAllTickersFromMoex() {
    stubFor(
        WireMock.get(WireMock.urlEqualTo("/moex-stock-api/stocks/tickers"))
            .willReturn(
                WireMock.aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "application/json;")
                    .withBody(File("src/test/resources/__files/moex-response/moex-tickers.json").readText(Charsets.UTF_8))
            )
    )
}

fun stubForAllTickersFromNyse() {
    stubFor(
        WireMock.get(WireMock.urlEqualTo("/yahoo-stock-api/stocks/tickers"))
            .willReturn(
                WireMock.aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "application/json;")
                    .withBody(File("src/test/resources/__files/yahoo-response/nyse-tickers.json").readText(Charsets.UTF_8))
            )
    )
}