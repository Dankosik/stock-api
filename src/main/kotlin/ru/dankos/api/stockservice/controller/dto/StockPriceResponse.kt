package ru.dankos.api.stockservice.controller.dto

class StockPriceResponse(
    val ticker: String,
    val moneyValue: MoneyValue,
)

data class MoneyValue(
    val integer: Int,
    val fractional: Int,
    val currency: String,
) {
    operator fun compareTo(other: MoneyValue) =
        compareValuesBy(this, other, MoneyValue::integer, MoneyValue::fractional)
}