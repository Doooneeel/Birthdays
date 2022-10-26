package ru.daniilglazkov.birthdays.domain.showmode.group

import ru.daniilglazkov.birthdays.domain.birthday.BirthdayDomain
import ru.daniilglazkov.birthdays.domain.zodiac.ZodiacGroupClassification
import ru.daniilglazkov.birthdays.domain.date.DateDifference
import ru.daniilglazkov.birthdays.domain.date.DateTextFormat
import ru.daniilglazkov.birthdays.domain.date.NextEvent
import ru.daniilglazkov.birthdays.domain.range.RangeTextFormat
import ru.daniilglazkov.birthdays.domain.zodiac.ZodiacDomain
import java.time.LocalDate

/**
 * @author Danil Glazkov on 01.08.2022, 21:28
 */
interface BirthdayGroupHeader {
    fun header(group: BirthdayGroupDomain): BirthdayDomain

    abstract class Abstract(private val makeHeader: MakeHeader) : BirthdayGroupHeader {
        override fun header(group: BirthdayGroupDomain): BirthdayDomain =
            group.makeHeader(makeHeader)
    }

    class Age(before: LocalDate) : Abstract(
        MakeHeader.Range(
            RangeTextFormat.Base(),
            BirthdayGroupHeaderPredicate.Range(
                DateDifference.YearsPlusOne(),
                before
            )
        )
    )

    class MonthAndYear(nextEvent: NextEvent) : Abstract(
        MakeHeader.BasedOnFirst(
            BirthdayGroupHeaderPredicate.MonthAndYear(nextEvent)
        )
    )

    class Date(format: DateTextFormat) : Abstract(
        MakeHeader.BasedOnFirst(
            BirthdayGroupHeaderPredicate.Date(format),
        )
    )

    class FirstCharOfName : Abstract(
        MakeHeader.BasedOnFirst(
            BirthdayGroupHeaderPredicate.FirstChar()
        )
    )

    class Zodiac(
        classification: ZodiacGroupClassification,
        zodiacDomainToHeaderMapper: ZodiacDomain.Mapper<String>,
    ) : Abstract(
        MakeHeader.BasedOnFirst(
            BirthdayGroupHeaderPredicate.Zodiac(
                classification,
                zodiacDomainToHeaderMapper
            )
        )
    )
}