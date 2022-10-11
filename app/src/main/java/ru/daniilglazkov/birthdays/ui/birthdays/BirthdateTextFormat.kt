package ru.daniilglazkov.birthdays.ui.birthdays

import ru.daniilglazkov.birthdays.R
import ru.daniilglazkov.birthdays.domain.date.DateDifference
import ru.daniilglazkov.birthdays.domain.date.DateTextFormat
import ru.daniilglazkov.birthdays.domain.date.NextEvent
import ru.daniilglazkov.birthdays.ui.core.resources.ProvideString
import java.time.LocalDate

/**
 * @author Danil Glazkov on 19.07.2022, 01:49
 */
interface BirthdateTextFormat {
    fun format(date: LocalDate): String


    class Date(private val dateFormat: DateTextFormat) : BirthdateTextFormat {
        override fun format(date: LocalDate) = dateFormat.format(date)
    }

    class Empty : BirthdateTextFormat {
        override fun format(date: LocalDate) = ""
    }

    abstract class AbstractDateDifference(
        private val range: DateDifference,
        private val now: LocalDate
    ) : BirthdateTextFormat {
        protected fun range(date: LocalDate): Int = range.difference(now, date)
    }

    class Age(
        private val string: ProvideString,
        before: LocalDate
    ) : AbstractDateDifference(DateDifference.YearsPlusOne(), before) {
        override fun format(date: LocalDate) = string.quantityString(R.plurals.age, range(date))
    }

    class DaysToBirthday(
        private val provideString: ProvideString,
        nextEvent: NextEvent,
        before: LocalDate
    ) : AbstractDateDifference(DateDifference.NextEventInDays(nextEvent), before) {
        override fun format(date: LocalDate) = when(val number = range(date)) {
            0 -> provideString.string(R.string.today)
            1 -> provideString.string(R.string.tomorrow)
            else -> provideString.quantityString(R.plurals.day, number)
        }
    }

    class DaysToBirthdaySheet(
        private val provideString: ProvideString,
        nextEvent: NextEvent,
        before: LocalDate
    ) : AbstractDateDifference(DateDifference.NextEventInDays(nextEvent), before) {
        override fun format(date: LocalDate): String = when(val range = range(date)) {
            0 -> provideString.string(R.string.today)
            else -> provideString.quantityString(R.plurals.left, range)
        }
    }
}