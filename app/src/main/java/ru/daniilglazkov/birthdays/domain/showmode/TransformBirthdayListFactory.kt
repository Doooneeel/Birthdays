package ru.daniilglazkov.birthdays.domain.showmode

import ru.daniilglazkov.birthdays.domain.showmode.age.AgeGroupClassification
import ru.daniilglazkov.birthdays.domain.showmode.TransformBirthdayList.SortingAndGrouping
import ru.daniilglazkov.birthdays.domain.showmode.group.BirthdayGroupHeader
import ru.daniilglazkov.birthdays.domain.showmode.group.GroupBirthdayList
import ru.daniilglazkov.birthdays.domain.showmode.sort.SortBirthdayList
import ru.daniilglazkov.birthdays.domain.showmode.sort.SortMode
import ru.daniilglazkov.birthdays.domain.showmode.split.SplitBirthdayList
import ru.daniilglazkov.birthdays.domain.zodiac.ZodiacGroupClassification
import ru.daniilglazkov.birthdays.domain.date.*
import ru.daniilglazkov.birthdays.domain.zodiac.ZodiacDomain
import java.time.LocalDate

/**
 * @author Danil Glazkov on 01.08.2022, 18:59
 */
interface TransformBirthdayListFactory {
    fun create(sort: SortMode, reverse: Boolean, group: Boolean): TransformBirthdayList

    class Base(
        private val zodiacGroupClassification: ZodiacGroupClassification,
        private val ageGroupClassification: AgeGroupClassification,
        private val nextEvent: NextEvent,
        private val now: LocalDate
    ) : TransformBirthdayListFactory {
        private val unitGroup = GroupBirthdayList.Unit()
        private val descendingSortByDayOfYear = SortBirthdayList.DayOfYearDescending()
        private val ascendingSortByDayOfYear = SortBirthdayList.DayOfYearAscending()

        override fun create(sort: SortMode, reverse: Boolean, group: Boolean) = when (sort) {
            SortMode.DATE -> {
                val nextEventInDays = DateDifference.NextEventInDays(nextEvent, now)
                SortingAndGrouping(
                    sort = if (reverse) SortBirthdayList.RangeDescending(nextEventInDays)
                    else SortBirthdayList.RangeAscending(nextEventInDays),

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
                sort = if (reverse) descendingSortByDayOfYear else ascendingSortByDayOfYear,
                group = if (group) GroupBirthdayList.Base(
                    split = SplitBirthdayList.Month(),
                    groupHeader = BirthdayGroupHeader.Date(DateTextFormat.Month())
                )
                else unitGroup
            )
            SortMode.YEAR -> {
                val differenceInYears = DateDifference.Years(now)
                SortingAndGrouping(
                    sort = if (reverse) SortBirthdayList.RangeDescending(differenceInYears)
                    else SortBirthdayList.RangeAscending(differenceInYears),

                    group = if (group) GroupBirthdayList.Base(
                        split = SplitBirthdayList.Year(), groupHeader = BirthdayGroupHeader.Date(
                            DateTextFormat.Year()
                        )
                    )
                    else unitGroup
                )
            }
            SortMode.ZODIAC -> {
                SortingAndGrouping(
                    sort = if (reverse) ascendingSortByDayOfYear else descendingSortByDayOfYear,
                    group = if (group) GroupBirthdayList.Base(
                        split = SplitBirthdayList.Zodiac(zodiacGroupClassification),
                        groupHeader = BirthdayGroupHeader.Zodiac(
                            zodiacGroupClassification,
                            ZodiacDomain.Mapper.ToHeader()
                        )
                    )
                    else unitGroup
                )
            }
        }
    }
}