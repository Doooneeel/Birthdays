package ru.daniilglazkov.birthdays.ui.birthdays

import ru.daniilglazkov.birthdays.domain.birthday.BirthdayType
import ru.daniilglazkov.birthdays.domain.date.DateTextFormat
import ru.daniilglazkov.birthdays.domain.date.NextEvent
import ru.daniilglazkov.birthdays.ui.core.resources.ProvideString
import java.time.LocalDate

/**
 * @author Danil Glazkov on 10.10.2022, 19:04
 */
interface BirthdayDomainToUiMapperFactory {
    fun create(type: BirthdayType): BirthdayDomainToUiMapper


    abstract class Abstract(
        resources: ProvideString,
        now: LocalDate,
    ) : BirthdayDomainToUiMapperFactory {
        protected val ageTextFormat = BirthdateTextFormat.Age(resources, now)
    }

    class Base(
        private val resources: ProvideString,
        private val zodiacTextFormat: ZodiacTextFormat,
        private val nextEvent: NextEvent,
        private val now: LocalDate,
        private val next: BirthdayDomainToUiMapperFactory = Error
    ) : Abstract(resources, now) {
        override fun create(type: BirthdayType): BirthdayDomainToUiMapper = when (type) {
            is BirthdayType.Base -> BirthdayDomainToUiMapper.Base(ageTextFormat,
                BirthdateTextFormat.DaysToBirthday(resources, nextEvent, now)
            )
            is BirthdayType.Header -> BirthdayDomainToUiMapper.Header()
            is BirthdayType.Today -> BirthdayDomainToUiMapper.Today(ageTextFormat)
            is BirthdayType.Zodiac -> BirthdayDomainToUiMapper.Zodiac(
                zodiacTextFormat.format(type)
            )
            else -> next.create(type)
        }
    }

    class Sheet(
        private val resources: ProvideString,
        private val nextEvent: NextEvent,
        private val now: LocalDate,
        private val next: BirthdayDomainToUiMapperFactory = Error
    ) : Abstract(resources, now) {
        private val dateTextFormat = BirthdateTextFormat.Date(DateTextFormat.Full())

        override fun create(type: BirthdayType): BirthdayDomainToUiMapper = when (type) {
            is BirthdayType.Today -> BirthdayDomainToUiMapper.TodaySheet(ageTextFormat, dateTextFormat,
                BirthdateTextFormat.DaysToBirthday(resources, nextEvent, now),
            )
            is BirthdayType.Base -> BirthdayDomainToUiMapper.BaseSheet(ageTextFormat, dateTextFormat,
                BirthdateTextFormat.DaysToBirthdaySheet(resources, nextEvent, now)
            )
            else -> next.create(type)
        }
    }

    object Error : BirthdayDomainToUiMapperFactory {
        override fun create(type: BirthdayType) =
            throw IllegalStateException("Unknown type: $type")
    }
}