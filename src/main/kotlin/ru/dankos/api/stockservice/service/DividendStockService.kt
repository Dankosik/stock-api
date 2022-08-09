package ru.dankos.api.stockservice.service

import org.springframework.stereotype.Service
import ru.dankos.api.stockservice.controller.dto.DividendResponse
import ru.dankos.api.stockservice.controller.dto.MoneyValue
import ru.dankos.api.stockservice.controller.dto.YieldValue
import ru.dankos.api.stockservice.extension.awaitSingle
import ru.tinkoff.piapi.contract.v1.Dividend
import ru.tinkoff.piapi.core.InstrumentsService
import java.time.Duration
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId

@Service
class DividendStockService(
    private val instrumentsService: InstrumentsService,
    private val commonStockService: CommonStockService
) {
    suspend fun getDividendsByTickerAndDate(ticker: String, date: LocalDate): List<DividendResponse> =
        instrumentsService.getDividends(
            commonStockService.getStockByTicker(ticker).figi,
            Instant.now(),
            Instant.now().plusSeconds(Duration.between(LocalDateTime.now(), date.atStartOfDay()).seconds)
        ).awaitSingle()
            .map { it.toDividendResponse(ticker) }
            .filter { it.paymentDate <= date }
            .sortedBy { it.paymentDate }

    private fun Dividend.toDividendResponse(ticker: String) =
        DividendResponse(
            moneyValue = MoneyValue(
                value = this.dividendNet.units.toInt(),
                minorUnits = this.dividendNet.nano,
                currency = this.dividendNet.currency
            ),
            ticker = ticker,
            paymentDate = LocalDate.ofInstant(
                Instant.ofEpochSecond(
                    this.paymentDate.seconds,
                    this.paymentDate.nanos.toLong()
                ),
                ZoneId.systemDefault()
            ),
            yieldValue = YieldValue(
                integer = this.yieldValue.units.toInt(),
                fractional = this.yieldValue.nano
            )
        )
}