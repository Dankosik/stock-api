package ru.dankos.api.stockservice.extension

import kotlinx.coroutines.reactor.awaitSingle
import reactor.kotlin.core.publisher.toMono
import java.util.concurrent.CompletableFuture

suspend fun <T> CompletableFuture<out T?>.awaitSingle(): T = this.toMono().awaitSingle()