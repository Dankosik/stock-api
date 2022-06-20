package ru.dankos.api.stockservice.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "caching")
class CaffeineProperties(
    val specs: Map<String, CacheSpec>
)

class CacheSpec(
    val timeout: Long,
)
