package ru.dankos.api.stockservice

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import ru.dankos.api.stockservice.controller.dto.MoneyValue

class MoneyValueTest {

    @Test
    internal fun `test compare integer value`() {
        val moneyValue = MoneyValue(210, 22, "RUR")
        val moneyValue2 = MoneyValue(229, 12, "RUR")

        assertAll(
            { assertFalse(moneyValue > moneyValue2) },
            { assertTrue(moneyValue < moneyValue2) }
        )

    }

    @Test
    internal fun `test compare fractional value`() {
        val moneyValue = MoneyValue(210, 22, "RUR")
        val moneyValue2 = MoneyValue(210, 23, "RUR")

        assertAll(
            { assertFalse(moneyValue > moneyValue2) },
            { assertTrue(moneyValue < moneyValue2) }
        )

    }
}