package ru.dankos.api.stockservice.controller.dto

import ru.dankos.api.stockservice.model.Exchanges

class StockBaseInfoResponse(
    val ticker: String,
    val stockName: String,
    val exchange: Exchanges,
)