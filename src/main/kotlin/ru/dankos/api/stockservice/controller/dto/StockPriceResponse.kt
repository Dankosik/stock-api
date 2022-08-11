package ru.dankos.api.stockservice.controller.dto

import java.time.LocalTime

class StockPriceResponse(
    val ticker: String,
    val moneyValue: MoneyValue,
    val time: LocalTime,
)

data class MoneyValue(
    val value: Int,
    val minorUnits: Int,
    val currency: String,
) {
    operator fun compareTo(other: MoneyValue) =
        compareValuesBy(this, other, MoneyValue::value, MoneyValue::minorUnits)
}