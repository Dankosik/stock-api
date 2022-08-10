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