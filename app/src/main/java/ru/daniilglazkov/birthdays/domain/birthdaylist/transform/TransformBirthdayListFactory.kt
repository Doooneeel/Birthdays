package ru.daniilglazkov.birthdays.domain.birthdaylist.transform

import ru.daniilglazkov.birthdays.domain.core.text.AddDelimiter
import ru.daniilglazkov.birthdays.domain.datetime.*
import ru.daniilglazkov.birthdays.domain.range.RangeTextFormat
import ru.daniilglazkov.birthdays.domain.birthdaylist.transform.age.AgeGroups
import ru.daniilglazkov.birthdays.domain.birthdaylist.transform.group.BirthdayGroupHeaderPredicate
import ru.daniilglazkov.birthdays.domain.birthdaylist.transform.group.GroupBirthdayList
import ru.daniilglazkov.birthdays.domain.birthdaylist.transform.group.MakeHeader
import ru.daniilglazkov.birthdays.domain.birthdaylist.transform.sort.BirthdayListSortPredicate
import ru.daniilglazkov.birthdays.domain.birthdaylist.transform.sort.SortBirthdayList
import ru.daniilglazkov.birthdays.domain.birthdaylist.transform.sort.SortMode
import ru.daniilglazkov.birthdays.domain.birthdaylist.transform.split.BirthdayListSplitPredicate
import ru.daniilglazkov.birthdays.domain.birthdaylist.transform.split.SplitBirthdayList
import ru.daniilglazkov.birthdays.domain.settings.SettingsDomain
import ru.daniilglazkov.birthdays.domain.zodiac.greek.GreekZodiacGroups
import java.time.LocalDate
import java.util.*

/**
 * @author Danil Glazkov on 01.08.2022, 18:59
 */
interface TransformBirthdayListFactory : SettingsDomain.Mapper<TransformBirthdayList> {

    class Base(
        private val greekZodiacGroup: GreekZodiacGroups,
        private val ageGroups: AgeGroups,
        private val calculateNextEvent: CalculateNextEvent,
        private val locale: Locale,
        private val now: LocalDate,
        private val next: TransformBirthdayListFactory = Error
    ) : TransformBirthdayListFactory {
        override fun map(sort: SortMode, reverse: Boolean, group: Boolean) : TransformBirthdayList {
            val transformBirthdayList = when(sort) {
                SortMode.DATE -> Date(calculateNextEvent, locale, now)
                SortMode.MONTH -> Month(locale)
                SortMode.NAME -> Name(locale)
                SortMode.YEAR -> Year()
                SortMode.AGE -> Age(ageGroups, now)
                SortMode.ZODIAC -> Zodiac(greekZodiacGroup)
                else -> next
            }
            return transformBirthdayList.map(sort, reverse, group)
        }
    }


    abstract class Abstract : TransformBirthdayListFactory {

        protected abstract val groupBirthdayList: GroupBirthdayList

        protected abstract val sortPredicate: BirthdayListSortPredicate<*>


        override fun map(sort: SortMode, reverse: Boolean, group: Boolean): TransformBirthdayList {
            return TransformBirthdayList.Base(
                sort = if (reverse) {
                    SortBirthdayList.Descending(sortPredicate)
                } else {
                    SortBirthdayList.Ascending(sortPredicate)
                },
                group = if (group) groupBirthdayList else GroupBirthdayList.Skip
            )
        }
    }

    class Date(nextEvent: CalculateNextEvent, locale: Locale, now: LocalDate) : Abstract() {

        private val nextEventInDays = DateDifference.Days.NextEvent(nextEvent, now)

        override val sortPredicate =  BirthdayListSortPredicate.Difference(nextEventInDays)

        override val groupBirthdayList = GroupBirthdayList.Base(
            split = SplitBirthdayList.Base(
                BirthdayListSplitPredicate.YearAndMonthNextEvent(nextEvent)
            ),
            makeHeader = MakeHeader.BasedOnFirst(
                BirthdayGroupHeaderPredicate.DateFormat(
                    DateTextFormat.MonthAndYearOfNextEvent(
                        nextEvent,
                        locale
                    )
                )
            )
        )
    }

    class Age(ageGroups: AgeGroups, now: LocalDate) : Abstract() {

        override val sortPredicate = BirthdayListSortPredicate.EpochDayDescending()

        override val groupBirthdayList = GroupBirthdayList.Base(
            split = SplitBirthdayList.Base(
                BirthdayListSplitPredicate.Difference(
                    DateDifference.Years.TurnsYearsOld(now),
                    ageGroups,
                )
            ),
            makeHeader = MakeHeader.Edges(
                RangeTextFormat.Base(AddDelimiter.Range()),
                BirthdayGroupHeaderPredicate.Difference(
                    DateDifference.Years.TurnsYearsOld(now)
                )
            )
        )
    }

    class Name(locale: Locale) : Abstract() {

        override val sortPredicate =  BirthdayListSortPredicate.Name(locale)

        override val groupBirthdayList = GroupBirthdayList.Base(
            split = SplitBirthdayList.Base(
                BirthdayListSplitPredicate.FirstCharacterOfName()
            ),
            makeHeader = MakeHeader.BasedOnFirst(
                BirthdayGroupHeaderPredicate.FirstCharacterOfName()
            )
        )
    }

    class Month(locale: Locale) : Abstract() {

        override val sortPredicate = BirthdayListSortPredicate.DayOfYear()

        override val groupBirthdayList = GroupBirthdayList.Base(
            split = SplitBirthdayList.Base(
                BirthdayListSplitPredicate.Month()
            ),
            makeHeader = MakeHeader.BasedOnFirst(
                BirthdayGroupHeaderPredicate.DateFormat(
                    DateTextFormat.Month(locale)
                )
            )
        )
    }

    class Year : Abstract() {

        override val sortPredicate = BirthdayListSortPredicate.EpochDayAscending()

        override val groupBirthdayList = GroupBirthdayList.Base(
            split = SplitBirthdayList.Base(
                BirthdayListSplitPredicate.Year()
            ),
            makeHeader = MakeHeader.BasedOnFirst(
                BirthdayGroupHeaderPredicate.DateFormat(
                    DateTextFormat.Year()
                )
            )
        )
    }

    class Zodiac(greekZodiacGroup: GreekZodiacGroups) : Abstract() {

        override val sortPredicate = BirthdayListSortPredicate.Zodiac(greekZodiacGroup)

        override val groupBirthdayList = GroupBirthdayList.Base(
            split = SplitBirthdayList.Base(
                predicate = BirthdayListSplitPredicate.Zodiac(greekZodiacGroup)
            ),
            makeHeader = MakeHeader.BasedOnFirst(
                BirthdayGroupHeaderPredicate.ZodiacPredicate(
                    greekZodiacGroup,
                    ru.daniilglazkov.birthdays.domain.zodiac.ZodiacDomain.Mapper.ToHeader()
                )
            )
        )
    }

    object Error : TransformBirthdayListFactory {
        override fun map(sort: SortMode, reverse: Boolean, group: Boolean) =
            throw IllegalStateException("Unknown sort mode: $sort")
    }
}