package ru.dankos.api.stockservice.controller.dto

class StockPriceRequest(
    val ticker: String,
    val moneyValue: MoneyValue,
)
