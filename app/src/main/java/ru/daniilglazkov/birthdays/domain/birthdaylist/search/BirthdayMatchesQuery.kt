package ru.daniilglazkov.birthdays.domain.birthdaylist.search

import ru.daniilglazkov.birthdays.domain.date.DateTextFormat
import java.time.LocalDate

/**
 * @author Danil Glazkov on 08.10.2022, 17:12
 */
interface BirthdayMatchesQuery {
    fun matches(query: String, name: String, date: LocalDate): Boolean

    abstract class Abstract : BirthdayMatchesQuery {
        protected fun CharSequence.containsAll(vararg values: Any): Boolean = values.any { value ->
            value.toString().contains(this)
        }
    }

    class Name : Abstract() {
        override fun matches(query: String, name: String, date: LocalDate): Boolean =
            query.containsAll(name)
    }

    class DateFormat(private val dateTextFormat: DateTextFormat) : Abstract() {
        override fun matches(query: String, name: String, date: LocalDate): Boolean =
            query.containsAll(dateTextFormat.format(date).lowercase())
    }

    class RawDate : Abstract() {
        override fun matches(query: String, name: String, date: LocalDate): Boolean =
            query.containsAll(date.dayOfMonth, date.year, date.monthValue)
    }
}