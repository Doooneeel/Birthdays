package ru.daniilglazkov.birthdays.domain.birthdays.showmode.group

import ru.daniilglazkov.birthdays.domain.date.DateDifference
import ru.daniilglazkov.birthdays.domain.date.DateTextFormat
import ru.daniilglazkov.birthdays.domain.date.NextEvent
import ru.daniilglazkov.birthdays.domain.range.RangeTextFormat
import java.time.LocalDate

/**
 * @author Danil Glazkov on 01.08.2022, 21:28
 */
interface BirthdayGroupHeader {
    fun header(group: BirthdayGroupDomain): BirthdayHeader

    abstract class Abstract(private val makeHeader: MakeHeader) : BirthdayGroupHeader {
        override fun header(group: BirthdayGroupDomain): BirthdayHeader {
            return group.makeHeader(makeHeader)
        }
    }
    class Age(before: LocalDate) : Abstract(
        MakeHeader.Range(
            RangeTextFormat.Base(),
            BirthdayGroupPredicate.Range(
                DateDifference.YearsPlusOne(),
                before
            )
        )
    )
    class MonthAndYear(nextEvent: NextEvent) : Abstract(
        MakeHeader.BasedOnFirst(
            BirthdayGroupPredicate.MonthAndYear(nextEvent)
        )
    )
    class Date(format: DateTextFormat) : Abstract(
        MakeHeader.BasedOnFirst(
            BirthdayGroupPredicate.Date(format)
        )
    )
    class FirstCharOfName : Abstract(
        MakeHeader.BasedOnFirst(
            BirthdayGroupPredicate.FirstChar()
        )
    )
}