package ru.dankos.api.stockservice.controller.dto

class SpbeStockPriceResponse(
    val ticker: String,
    val moneyValue: SpbeMoneyValue,
)

data class SpbeMoneyValue(
    val value: Int,
    val minorUnits: Int,
    val currency: String,
)