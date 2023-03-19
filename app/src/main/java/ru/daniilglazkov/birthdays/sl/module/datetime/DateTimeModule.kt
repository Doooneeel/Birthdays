package ru.daniilglazkov.birthdays.sl.module.datetime

import ru.daniilglazkov.birthdays.domain.datetime.*
import java.time.*

/**
 * @author Danil Glazkov on 30.10.2022, 10:01
 */
interface DateTimeModule : ProvideCurrentDate, ProvideNextEvent, ProvideEventIsToday,
    ProvideCurrentTimeInMillis, ProvideZoneOffset
{
    class Base : DateTimeModule {

        private val now get() = OffsetDateTime.now()

        override fun zoneOffset(): ZoneOffset = now.offset

        override fun provideCurrentDate(): LocalDate = now.toLocalDate()

        override fun provideEventIsToday(): EventIsToday = EventIsToday.Base(provideCurrentDate())

        override fun provideCurrentTimeInMillis() = CurrentTimeInMillis.Base

        override fun provideNextEvent(): CalculateNextEvent {
            val currentDate = provideCurrentDate()

            return CalculateNextEvent.Base(
                EventIsToday.Base(currentDate),
                IsLeapDay.Base(),
                DetermineLeapDay.Base(),
                currentDate
            )
        }
    }
}