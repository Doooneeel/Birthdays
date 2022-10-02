package ru.daniilglazkov.birthdays.domain.birthdays.showmode

import ru.daniilglazkov.birthdays.domain.birthdays.showmode.TransformBirthdayList.SortingAndGrouping
import ru.daniilglazkov.birthdays.domain.birthdays.showmode.group.BirthdayGroupHeader
import ru.daniilglazkov.birthdays.domain.birthdays.showmode.group.GroupBirthdayList
import ru.daniilglazkov.birthdays.domain.birthdays.showmode.sort.SortBirthdayList.*
import ru.daniilglazkov.birthdays.domain.birthdays.showmode.sort.SortMode
import ru.daniilglazkov.birthdays.domain.birthdays.showmode.split.SplitBirthdayList
import ru.daniilglazkov.birthdays.domain.date.DateDifference
import ru.daniilglazkov.birthdays.domain.date.DateTextFormat
import ru.daniilglazkov.birthdays.domain.date.NextEvent
import java.time.LocalDate

/**
 * @author Danil Glazkov on 01.08.2022, 18:59
 */
interface TransformBirthdayListFactory {
    fun create(sort: SortMode, reverse: Boolean, group: Boolean): TransformBirthdayList

    class Base(private val nextEvent: NextEvent) : TransformBirthdayListFactory {
        private val unitGroup = GroupBirthdayList.Unit()
        private val now = LocalDate.now()

        override fun create(sort: SortMode, reverse: Boolean, group: Boolean) = when (sort) {
            SortMode.DATE -> {
                val range = DateDifference.NextEventInDays(nextEvent)
                SortingAndGrouping(
                    sort = if (reverse) RangeDescending(range, now) else RangeAscending(range, now),
                    group = if (group) GroupBirthdayList.Base(
                        split = SplitBirthdayList.MonthsByYears(nextEvent),
                        groupHeader = BirthdayGroupHeader.MonthAndYear(nextEvent)
                    )
                    else unitGroup
                )
            }
            SortMode.AGE -> SortingAndGrouping(
                sort = if (reverse) AgeDescending() else AgeAscending(),
                group = if (group) GroupBirthdayList.Base(
                    split = SplitBirthdayList.Age(now),
                    groupHeader = BirthdayGroupHeader.Age(now)
                )
                else unitGroup
            )
            SortMode.NAME -> SortingAndGrouping(
                sort = if (reverse) NameDescending() else NameAscending(),
                group = if (group) GroupBirthdayList.Base(
                    split = SplitBirthdayList.Name(),
                    groupHeader = BirthdayGroupHeader.FirstCharOfName()
                )
                else unitGroup
            )
            SortMode.MONTH -> SortingAndGrouping(
                sort = if (reverse) MonthDescending() else MonthAscending(),
                group = if (group) GroupBirthdayList.Base(
                    split = SplitBirthdayList.Month(),
                    groupHeader = BirthdayGroupHeader.Date(DateTextFormat.Month())
                )
                else unitGroup
            )
            SortMode.YEAR -> {
                val difference = DateDifference.Years()
                SortingAndGrouping(
                    sort = if (reverse) RangeAscending(difference, now)
                    else RangeDescending(difference, now),

                    group = if (group) GroupBirthdayList.Base(
                        split = SplitBirthdayList.Year(),
                        groupHeader = BirthdayGroupHeader.Date(DateTextFormat.Year())
                    )
                    else unitGroup
                )
            }
        }
    }
}