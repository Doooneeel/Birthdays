package ru.daniilglazkov.birthdays.domain.date

import java.time.LocalDate
import java.time.temporal.ChronoUnit
import kotlin.math.abs

/**
 * @author Danil Glazkov on 19.07.2022, 01:30
 */
interface DateDifference {
    fun difference(before: LocalDate, after: LocalDate): Int

    abstract class Abstract(private val chronoUnit: ChronoUnit) : DateDifference {
        override fun difference(before: LocalDate, after: LocalDate) =
            abs(chronoUnit.between(before, after)).toInt()
    }

    class Years : Abstract(ChronoUnit.YEARS)
    class Days : Abstract(ChronoUnit.DAYS)
    class Months : Abstract(ChronoUnit.MONTHS)

    class YearsPlusOne : Abstract(ChronoUnit.YEARS) {
        override fun difference(before: LocalDate, after: LocalDate) =
            super.difference(before, after).inc()
    }

    class NextEventInDays(
        private val nextEvent: NextEvent,
    ) : Abstract(ChronoUnit.DAYS) {
        override fun difference(before: LocalDate, after: LocalDate): Int =
            super.difference(nextEvent.nextEvent(after), before)
    }
}