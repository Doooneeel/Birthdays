package ru.daniilglazkov.birthdays.domain.date

import java.time.LocalDate
import java.time.temporal.ChronoUnit
import kotlin.math.abs

/**
 * @author Danil Glazkov on 19.07.2022, 01:30
 */
interface DateDifference {
    fun difference(date: LocalDate): Int

    abstract class Abstract(
        private val chronoUnit: ChronoUnit,
        private val now: LocalDate
    ) : DateDifference {
        override fun difference(date: LocalDate): Int =
            abs(chronoUnit.between(now, date)).toInt()
    }

    class Years(now: LocalDate) : Abstract(ChronoUnit.YEARS, now)

    class TurnsYearsOld(now: LocalDate) : Abstract(ChronoUnit.YEARS, now) {
        override fun difference(date: LocalDate): Int = super.difference(date) + 1
    }

    abstract class AbstractNextEvent(
        chronoUnit: ChronoUnit,
        private val nextEvent: NextEvent,
        now: LocalDate
    ) : Abstract(chronoUnit, now) {
        override fun difference(date: LocalDate): Int =
            super.difference(nextEvent.nextEvent(date))
    }

    class NextEventInDays(nextEvent: NextEvent, now: LocalDate) : AbstractNextEvent(
        ChronoUnit.DAYS,
        nextEvent,
        now
    )
}