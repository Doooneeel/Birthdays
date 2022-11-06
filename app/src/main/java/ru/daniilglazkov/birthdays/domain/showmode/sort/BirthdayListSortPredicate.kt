package ru.daniilglazkov.birthdays.domain.showmode.sort

import ru.daniilglazkov.birthdays.domain.birthday.BirthdayDomain
import ru.daniilglazkov.birthdays.domain.birthday.BirthdayType
import ru.daniilglazkov.birthdays.domain.date.DateDifference
import java.time.LocalDate

/**
 * @author Danil Glazkov on 10.09.2022, 18:20
 */
interface BirthdayListSortPredicate<T : Comparable<T>> : BirthdayDomain.Mapper<T> {

    class Range(private val range: DateDifference) : BirthdayListSortPredicate<Int> {
        override fun map(id: Int, name: String, date: LocalDate, type: BirthdayType): Int =
            range.difference(date)
    }

    class Age : BirthdayDomain.Mapper<Long> {
        override fun map(id: Int, name: String, date: LocalDate, type: BirthdayType): Long =
            -date.toEpochDay()
    }

    class Name : BirthdayListSortPredicate<String> {
        override fun map(id: Int, name: String, date: LocalDate, type: BirthdayType): String =
            name
    }

    class DayOfYear : BirthdayListSortPredicate<Int> {
        override fun map(id: Int, name: String, date: LocalDate, type: BirthdayType): Int =
            date.dayOfYear
    }
}