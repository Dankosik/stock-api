package ru.dankos.api.stockservice.controller.dto

class StockPriceResponse(
    val ticker: String,
    val companyName: String,
    val moneyValue: MoneyValue,
)

data class MoneyValue(
    val integer: Int,
    val fractional: Int,
    val currency: String,
)