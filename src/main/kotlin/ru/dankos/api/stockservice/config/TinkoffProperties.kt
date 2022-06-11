package ru.dankos.api.stockservice.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "tinkoff")
class TinkoffProperties(
    val api: Api,
)

class Api(
    val token: String,
    val moex: Moex,
    val spbe: Spbe,
)

class Moex(
    val classCode: String
)

class Spbe(
    val classCode: String
)
