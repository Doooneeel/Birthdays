package ru.daniilglazkov.birthdays.sl.module

import ru.daniilglazkov.birthdays.domain.date.*
import ru.daniilglazkov.birthdays.sl.core.ProvideCurrentDate
import ru.daniilglazkov.birthdays.sl.core.ProvideNextEvent
import java.time.LocalDate

/**
 * @author Danil Glazkov on 30.10.2022, 10:01
 */
interface DateModule : ProvideCurrentDate, ProvideNextEvent, ProvideDateDifferenceNextEventInDays,
    ProvideEventIsToday {

    class Base(private val now: LocalDate) : DateModule {
        private val eventIsToday = EventIsToday.Base(now)
        private val nextEvent = NextEvent.Base(
            IsLeapDay.Base(),
            EventIsToday.Base(now),
            HandlePassedDate.Base(now),
            DetermineLeapDay.Base(),
            now
        )
        private val nextEventInDays = DateDifference.NextEventInDays(nextEvent, now)

        override fun dateDifferenceNextEvent(): DateDifference = nextEventInDays
        override fun eventIsToday(): EventIsToday = eventIsToday
        override fun provideCurrentDate(): LocalDate = now
        override fun provideNextEvent(): NextEvent = nextEvent
    }
}

interface ProvideDateDifferenceNextEventInDays {
    fun dateDifferenceNextEvent(): DateDifference
}

interface ProvideEventIsToday {
    fun eventIsToday(): EventIsToday
}
