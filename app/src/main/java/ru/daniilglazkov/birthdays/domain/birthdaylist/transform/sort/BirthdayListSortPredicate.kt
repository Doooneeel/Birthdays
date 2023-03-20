package ru.daniilglazkov.birthdays.domain.birthdaylist.transform.sort

import ru.daniilglazkov.birthdays.domain.birthday.BirthdayDomain
import ru.daniilglazkov.birthdays.domain.birthday.BirthdayType
import ru.daniilglazkov.birthdays.domain.datetime.DateDifference
import ru.daniilglazkov.birthdays.domain.zodiac.greek.GreekZodiacDomain
import ru.daniilglazkov.birthdays.domain.zodiac.greek.GreekZodiacGroups
import java.time.LocalDate
import java.util.*

/**
 * @author Danil Glazkov on 10.09.2022, 18:20
 */
interface BirthdayListSortPredicate<T : Comparable<T>> : BirthdayDomain.Mapper<T> {

    class Difference(private val difference: DateDifference) : BirthdayListSortPredicate<Int> {
        override fun map(id: Int, name: String, date: LocalDate, type: BirthdayType): Int =
            difference.difference(date)
    }

    class EpochDayAscending : BirthdayListSortPredicate<Long> {
        override fun map(id: Int, name: String, date: LocalDate, type: BirthdayType): Long =
            date.toEpochDay()
    }

    class EpochDayDescending : BirthdayListSortPredicate<Long> {
        override fun map(id: Int, name: String, date: LocalDate, type: BirthdayType): Long =
            -date.toEpochDay()
    }

    class Name(private val locale: Locale) : BirthdayListSortPredicate<String> {
        override fun map(id: Int, name: String, date: LocalDate, type: BirthdayType): String =
            name.lowercase(locale)
    }

    class DayOfYear : BirthdayListSortPredicate<Int> {
        override fun map(id: Int, name: String, date: LocalDate, type: BirthdayType): Int =
            date.dayOfYear
    }

    class Zodiac(
        private val greekZodiac: GreekZodiacGroups
    ) : BirthdayListSortPredicate<GreekZodiacDomain> {
        override fun map(id: Int, name: String, date: LocalDate, type: BirthdayType) =
            greekZodiac.group(date.dayOfYear)
    }
}