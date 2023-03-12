package ru.daniilglazkov.birthdays.domain.birthdaylist.transform.split

import ru.daniilglazkov.birthdays.domain.birthday.*
import ru.daniilglazkov.birthdays.domain.date.*
import ru.daniilglazkov.birthdays.domain.range.*
import ru.daniilglazkov.birthdays.domain.zodiac.greek.GreekZodiacDomain
import ru.daniilglazkov.birthdays.domain.zodiac.greek.GreekZodiacGroups
import java.time.LocalDate

/**
 * @author Danil Glazkov on 17.09.2022, 04:24
 */
interface BirthdayListSplitPredicate<T> : BirthdayDomain.Mapper<T> {

    class Year : BirthdayListSplitPredicate<Int> {
        override fun map(id: Int, name: String, date: LocalDate, type: BirthdayType): Int =
            date.year
    }

    class YearAndMonthNextEvent(
        private val nextEvent: CalculateNextEvent,
    ) : BirthdayListSplitPredicate<String> {
        override fun map(id: Int, name: String, date: LocalDate, type: BirthdayType): String =
            nextEvent.nextEvent(date).run { "$year $monthValue" }
    }

    class Month : BirthdayListSplitPredicate<Int> {
        override fun map(id: Int, name: String, date: LocalDate, type: BirthdayType): Int =
            date.monthValue
    }

    class FirstCharacterOfName : BirthdayListSplitPredicate<String> {
        override fun map(id: Int, name: String, date: LocalDate, type: BirthdayType): String =
            if (name.isBlank()) "" else name.first().uppercase()
    }

    class Zodiac(
        private val group: GreekZodiacGroups,
    ) : BirthdayListSplitPredicate<GreekZodiacDomain> {
        override fun map(id: Int, name: String, date: LocalDate, type: BirthdayType) =
            group.group(date.dayOfYear)
    }
    
    class Difference<T : Range<Int>>(
        private val dateDifference: DateDifference,
        private val rangeGroups: RangeGroups<T, Int>,
    ) : BirthdayListSplitPredicate<T> {
        override fun map(id: Int, name: String, date: LocalDate, type: BirthdayType): T =
            rangeGroups.group(dateDifference.difference(date))
    }
}