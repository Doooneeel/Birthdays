package ru.daniilglazkov.birthdays.domain.birthdaylist.search

import ru.daniilglazkov.birthdays.domain.birthday.BirthdayDomain
import ru.daniilglazkov.birthdays.domain.birthday.BirthdayType
import ru.daniilglazkov.birthdays.domain.core.text.NormalizeQuery
import ru.daniilglazkov.birthdays.domain.datetime.CalculateNextEvent
import ru.daniilglazkov.birthdays.domain.datetime.DateTextFormat
import ru.daniilglazkov.birthdays.domain.zodiac.ZodiacDomain
import java.time.LocalDate
import java.util.*

/**
 * @author Danil Glazkov on 19.11.2022, 04:28
 */
interface BirthdayDomainToQueryMapper : BirthdayDomain.Mapper<List<String>> {

    class Name(private val normalizeQuery: NormalizeQuery) : BirthdayDomainToQueryMapper {
        override fun map(id: Int, name: String, date: LocalDate, type: BirthdayType): List<String> =
            normalizeQuery.normalize(name)
    }

    class WithNextEvent(
        private val nextEvent: CalculateNextEvent,
        private val queryMapper: BirthdayDomainToQueryMapper
    ) : BirthdayDomainToQueryMapper {
        override fun map(id: Int, name: String, date: LocalDate, type: BirthdayType): List<String> =
            queryMapper.map(id, name, nextEvent.nextEvent(date), type)
    }

    class ZodiacMapper(
        private val zodiacMapper: BirthdayDomain.Mapper<ZodiacDomain>,
        private val toQuery: ZodiacDomain.Mapper<String>
    ) : BirthdayDomainToQueryMapper {
        override fun map(id: Int, name: String, date: LocalDate, type: BirthdayType): List<String> {
            val zodiac: ZodiacDomain = zodiacMapper.map(id, name, date, type)
            val query: String = zodiac.map(toQuery)

            return listOf(query)
        }
    }

    class Date(
        private val month: DateTextFormat,
        private val dayOfWeek: DateTextFormat,
        private val locale: Locale
    ) : BirthdayDomainToQueryMapper {
        override fun map(id: Int, name: String, date: LocalDate, type: BirthdayType): List<String> {
            val dayOfMonth: String = date.dayOfMonth.toString()
            val year: String = date.year.toString()

            val month: String = month.format(date).lowercase(locale)
            val dayOfWeek: String = dayOfWeek.format(date).lowercase(locale)

            return listOf(dayOfMonth, month, year, dayOfWeek)
        }
    }
}