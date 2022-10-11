package ru.daniilglazkov.birthdays.domain.showmode.split

import ru.daniilglazkov.birthdays.domain.showmode.age.AgeGroupClassification
import ru.daniilglazkov.birthdays.domain.showmode.age.AgeRangeCategory
import ru.daniilglazkov.birthdays.domain.birthday.BirthdayDomain
import ru.daniilglazkov.birthdays.domain.birthday.BirthdayType
import ru.daniilglazkov.birthdays.domain.showmode.zodiac.ZodiacGroupClassification
import ru.daniilglazkov.birthdays.domain.showmode.zodiac.ZodiacRangeCategory
import ru.daniilglazkov.birthdays.domain.date.DateDifference
import ru.daniilglazkov.birthdays.domain.date.NextEvent
import ru.daniilglazkov.birthdays.domain.range.RangeCategory
import ru.daniilglazkov.birthdays.domain.range.RangeGroup
import java.time.LocalDate

/**
 * @author Danil Glazkov on 17.09.2022, 04:24
 */
interface BirthdayListSplitPredicate<T> : BirthdayDomain.Mapper<T> {

    class Year : BirthdayListSplitPredicate<Int> {
        override fun map(id: Int, name: String, date: LocalDate, type: BirthdayType): Int =
            date.year
    }

    class MonthsByYears(private val nextEvent: NextEvent) : BirthdayListSplitPredicate<String> {
        override fun map(id: Int, name: String, date: LocalDate, type: BirthdayType): String =
            nextEvent.nextEvent(date).run { "$year $monthValue" }
    }

    class Month : BirthdayListSplitPredicate<Int> {
        override fun map(id: Int, name: String, date: LocalDate, type: BirthdayType): Int =
            date.monthValue
    }

    class FirstCharacter : BirthdayListSplitPredicate<Char> {
        override fun map(id: Int, name: String, date: LocalDate, type: BirthdayType): Char =
            if (name.isNotEmpty()) name[0].uppercaseChar() else Char.MIN_VALUE
    }
    
    abstract class AbstractRange<T : RangeCategory<Int>>(
        private val dateDifference: DateDifference,
        private val rangeGroup: RangeGroup<T, Int>,
        private val before: LocalDate
    ) : BirthdayListSplitPredicate<T> {
        override fun map(id: Int, name: String, date: LocalDate, type: BirthdayType): T =
            rangeGroup.group(dateDifference.difference(before, date))
    }

    class Age(
        before: LocalDate,
        classification: AgeGroupClassification,
    ) : AbstractRange<AgeRangeCategory>(
        DateDifference.YearsPlusOne(),
        classification,
        before
    )

    class Zodiac(
        private val classification: ZodiacGroupClassification,
    ) : BirthdayListSplitPredicate<ZodiacRangeCategory> {
        override fun map(id: Int, name: String, date: LocalDate, type: BirthdayType) =
            classification.group(date.dayOfYear)
    }
}