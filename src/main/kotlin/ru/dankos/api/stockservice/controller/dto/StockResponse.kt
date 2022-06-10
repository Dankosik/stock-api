package ru.dankos.api.stockservice.controller.dto

class StockResponse(
    val ticker: String,
    val name: String,
    val moneyValue: MoneyValue,
)

data class MoneyValue(
    val integer: Int,
    val fractional: Int,
    val currency: String,
)