package ru.daniilglazkov.birthdays.domain.datetime

import java.time.LocalDate

/**
 * @author Danil Glazkov on 11.02.2023, 16:24
 */
abstract class BaseDateTest {

    protected fun runThroughAllDateVariations(date: (LocalDate) -> Unit) {
        var start = LocalDate.of(1500, 1, 1)

        repeat(366 * 1500) {
            date.invoke(start)
            start = start.plusDays(1)
        }
    }
}