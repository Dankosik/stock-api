package ru.dankos.api.stockservice.client.dto

import ru.dankos.api.stockservice.controller.dto.MoneyValue
import java.time.LocalTime

data class MoexStockPriceResponse(
    val ticker: String,
    val moneyValue: MoneyValue,
    val time: LocalTime,
)