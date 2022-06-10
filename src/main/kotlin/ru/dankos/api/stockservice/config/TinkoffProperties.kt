package ru.dankos.api.stockservice.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "tinkoff")
class TinkoffProperties(
    val token: String
)
