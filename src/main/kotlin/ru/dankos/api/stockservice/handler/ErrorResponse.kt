package ru.dankos.api.stockservice.handler

import java.util.*

class ErrorResponse(
    val message: String?,
    val timestamp: Date,
    val errorCode: Int,
    val errorMessage: String
)
