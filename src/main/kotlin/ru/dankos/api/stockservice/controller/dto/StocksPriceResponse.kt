package ru.dankos.api.stockservice.controller.dto

data class StocksPriceResponse(
    val stock: List<StockPriceResponse>
)