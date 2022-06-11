package ru.dankos.api.stockservice.controller.dto

class MoexStockPriceResponse(
    val ticker: String,
    val moneyValue: MoexMoneyValue,
)

data class MoexMoneyValue(
    val integer: Int,
    val fractional: Int,
    val currency: String,
)