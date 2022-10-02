package ru.daniilglazkov.birthdays.domain.birthdays.showmode.sort

import ru.daniilglazkov.birthdays.domain.birthdays.BirthdayDomain
import ru.daniilglazkov.birthdays.domain.birthdays.BirthdayListDomain
import ru.daniilglazkov.birthdays.domain.core.Sort
import ru.daniilglazkov.birthdays.domain.date.DateDifference
import java.time.LocalDate

import ru.daniilglazkov.birthdays.domain.birthdays.showmode.sort.BirthdayListSortPredicate as Predicate

/**
 * @author Danil Glazkov on 18.08.2022, 19:04
 */
interface SortBirthdayList : Sort<BirthdayListDomain> {

    abstract class Abstract(
        private val sortMapper: BirthdayListDomain.Mapper<BirthdayListDomain>
    ) : SortBirthdayList {
        override fun sort(data: BirthdayListDomain): BirthdayListDomain = data.map(sortMapper)
    }
    abstract class Ascending<T : Comparable<T>>(predicate: BirthdayDomain.Mapper<T>) : Abstract(
        BirthdayListDomain.SortMapper.Ascending(predicate)
    )
    abstract class Descending<T : Comparable<T>>(predicate: BirthdayDomain.Mapper<T>) : Abstract(
        BirthdayListDomain.SortMapper.Descending(predicate)
    )

    class MonthAscending : Ascending<Int>(Predicate.Month())
    class MonthDescending : Descending<Int>(Predicate.Month())

    class RangeAscending(range: DateDifference, before: LocalDate) : Ascending<Int>(
        Predicate.Range(range, before)
    )

    class RangeDescending(range: DateDifference, before: LocalDate) : Descending<Int>(
        Predicate.Range(range, before)
    )

    class AgeAscending : Ascending<Long>(Predicate.Age())
    class AgeDescending : Descending<Long>(Predicate.Age())

    class NameAscending : Ascending<String>(Predicate.Name())
    class NameDescending : Descending<String>(Predicate.Name())

    class Unit : Sort.Unit<BirthdayListDomain>(), SortBirthdayList
}