package ru.dankos.api.stockservice.controller.dto

import java.time.LocalDate

class DividendResponse(
    val ticker: String,
    val moneyValue: MoneyValue,
    val paymentDate: LocalDate,
    val yieldValue: YieldValue,
)

class YieldValue(
    val integer: Int,
    val fractional: Int,
)
