package ru.daniilglazkov.birthdays.sl.module

import ru.daniilglazkov.birthdays.domain.date.*
import ru.daniilglazkov.birthdays.sl.core.*
import java.time.LocalDate

/**
 * @author Danil Glazkov on 30.10.2022, 10:01
 */
interface DateModule : ProvideCurrentDate, ProvideNextEvent, ProvideEventIsToday {

    class Base(private val now: LocalDate) : DateModule {

        private val eventIsToday = EventIsToday.Base(now)

        private val calculateNextEvent = CalculateNextEvent.Base(
            eventIsToday,
            IsLeapDay.Base(),
            DetermineLeapDay.Base(),
            now
        )

        override fun provideEventIsToday(): EventIsToday = eventIsToday

        override fun provideCurrentDate(): LocalDate = now

        override fun provideNextEvent(): CalculateNextEvent = calculateNextEvent
    }
}