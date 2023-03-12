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

    abstract class AbstractNextEvent(
        chronoUnit: ChronoUnit,
        private val calculateNextEvent: CalculateNextEvent,
        now: LocalDate
    ) : Abstract(chronoUnit, now) {
        override fun difference(date: LocalDate): Int = super.difference(
            calculateNextEvent.nextEvent(date)
        )
    }

    interface Years : DateDifference {

        class Base(now: LocalDate) : Abstract(ChronoUnit.YEARS, now), Years

        class TurnsYearsOld(now: LocalDate) : Abstract(ChronoUnit.YEARS, now), Years {
            override fun difference(date: LocalDate): Int = super.difference(date) + 1
        }
    }

    interface Days : DateDifference {

        class Base(now: LocalDate) : Abstract(ChronoUnit.DAYS, now), Days

        class NextEvent(calculateNextEvent: CalculateNextEvent, now: LocalDate) :
            AbstractNextEvent(ChronoUnit.DAYS, calculateNextEvent, now), Days
    }

    data class Test(var result: Int = -1) : DateDifference, Days, Years {
        val calledList = mutableListOf<LocalDate>()

        override fun difference(date: LocalDate): Int {
            calledList.add(date)
            return result
        }
    }
}