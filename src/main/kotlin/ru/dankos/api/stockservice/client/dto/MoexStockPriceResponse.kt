package ru.dankos.api.stockservice.client.dto

import java.time.LocalTime

data class MoexStockPriceResponse(
    val ticker: String,
    val moneyValue: MoneyValue,
    val time: LocalTime,
)

data class MoneyValue(
    val integer: Int,
    val fractional: String,
    val currency: String,
)