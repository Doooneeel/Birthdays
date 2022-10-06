package ru.daniilglazkov.birthdays.domain.birthdays.showmode

import ru.daniilglazkov.birthdays.core.resources.ProvideString
import ru.daniilglazkov.birthdays.domain.birthdays.AgeGroupClassification
import ru.daniilglazkov.birthdays.domain.birthdays.showmode.TransformBirthdayList.SortingAndGrouping
import ru.daniilglazkov.birthdays.domain.birthdays.showmode.group.BirthdayGroupHeader
import ru.daniilglazkov.birthdays.domain.birthdays.showmode.group.GroupBirthdayList
import ru.daniilglazkov.birthdays.domain.birthdays.showmode.sort.SortBirthdayList
import ru.daniilglazkov.birthdays.domain.birthdays.showmode.sort.SortMode
import ru.daniilglazkov.birthdays.domain.birthdays.showmode.split.SplitBirthdayList
import ru.daniilglazkov.birthdays.domain.birthdays.zodiac.ZodiacGroupClassification
import ru.daniilglazkov.birthdays.domain.birthdays.zodiac.ZodiacTextFormat
import ru.daniilglazkov.birthdays.domain.date.*
import java.time.LocalDate

/**
 * @author Danil Glazkov on 01.08.2022, 18:59
 */
interface TransformBirthdayListFactory {
    fun create(sort: SortMode, reverse: Boolean, group: Boolean): TransformBirthdayList

    class Base(
        private val nextEvent: NextEvent,
        provideString: ProvideString,
    ) : TransformBirthdayListFactory {
        private val now = LocalDate.now()

        private val zodiacGroupClassification = ZodiacGroupClassification.Base()
        private val ageGroupClassification = AgeGroupClassification.Base()
        private val zodiacTextFormat = ZodiacTextFormat.Base(provideString)
        private val unitGroup = GroupBirthdayList.Unit()
        private val nextEventInDays = DateDifference.NextEventInDays(nextEvent)
        private val differenceInYears = DateDifference.Years()

        override fun create(sort: SortMode, reverse: Boolean, group: Boolean) = when (sort) {
            SortMode.DATE -> {
                SortingAndGrouping(
                    sort = if (reverse) SortBirthdayList.RangeDescending(nextEventInDays, now)
                    else SortBirthdayList.RangeAscending(nextEventInDays, now),

                    group = if (group) GroupBirthdayList.Base(
                        split = SplitBirthdayList.MonthsByYears(nextEvent),
                        groupHeader = BirthdayGroupHeader.MonthAndYear(nextEvent)
                    )
                    else unitGroup
                )
            }
            SortMode.AGE -> SortingAndGrouping(
                sort = if (reverse) SortBirthdayList.AgeDescending()
                else SortBirthdayList.AgeAscending(),

                group = if (group) GroupBirthdayList.Base(
                    split = SplitBirthdayList.Age(now, ageGroupClassification),
                    groupHeader = BirthdayGroupHeader.Age(now)
                )
                else unitGroup
            )
            SortMode.NAME -> SortingAndGrouping(
                sort = if (reverse) SortBirthdayList.NameDescending()
                else SortBirthdayList.NameAscending(),

                group = if (group) GroupBirthdayList.Base(
                    split = SplitBirthdayList.Name(),
                    groupHeader = BirthdayGroupHeader.FirstCharOfName()
                )
                else unitGroup
            )
            SortMode.MONTH -> SortingAndGrouping(
                sort = if (reverse) SortBirthdayList.MonthDescending()
                else SortBirthdayList.MonthAscending(),

                group = if (group) GroupBirthdayList.Base(
                    split = SplitBirthdayList.Month(),
                    groupHeader = BirthdayGroupHeader.Date(DateTextFormat.Month())
                )
                else unitGroup
            )
            SortMode.YEAR -> {
                SortingAndGrouping(
                    sort = if (reverse) SortBirthdayList.RangeAscending(differenceInYears, now)
                    else SortBirthdayList.RangeDescending(differenceInYears, now),

                    group = if (group) GroupBirthdayList.Base(
                        split = SplitBirthdayList.Year(),
                        groupHeader = BirthdayGroupHeader.Date(DateTextFormat.Year())
                    )
                    else unitGroup
                )
            }
            SortMode.ZODIAC -> {
                SortingAndGrouping(
                    sort = if (reverse) SortBirthdayList.ZodiacAscending()
                    else SortBirthdayList.ZodiacDescending(),

                    group = if (group) GroupBirthdayList.Base(
                        split = SplitBirthdayList.Zodiac(zodiacGroupClassification),
                        groupHeader = BirthdayGroupHeader.Zodiac(
                            zodiacGroupClassification,
                            zodiacTextFormat
                        )
                    )
                    else unitGroup
                )
            }
        }
    }
}