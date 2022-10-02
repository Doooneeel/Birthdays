package ru.daniilglazkov.birthdays.ui.birthdays

import ru.daniilglazkov.birthdays.domain.birthdays.BirthdayDomain
import ru.daniilglazkov.birthdays.domain.birthdays.BirthdayType
import ru.daniilglazkov.birthdays.domain.date.DateDifference
import java.time.LocalDate

/**
 * @author Danil Glazkov on 01.09.2022, 21:35
 */
interface BirthdayDomainToUiMapper : BirthdayDomain.Mapper<BirthdayUi> {

    //TODO refactoring!!!
    class Base(
        private val turnsTextMapper: BirthdateTextFormat,
        private val dateTextMapper: BirthdateTextFormat,
        private val untilTextMapper: BirthdateTextFormat,
        private val dateDifference: DateDifference,
        private val now: LocalDate
    ) : BirthdayDomainToUiMapper {
        override fun map(id: Int, name: String, date: LocalDate, type: BirthdayType) =
            if (type.matches(BirthdayType.Base)) {
                BirthdayUi.Base(id, name,
                    turns = turnsTextMapper.format(date),
                    date = dateTextMapper.format(date),
                    until = untilTextMapper.format(date),
                )
            }
            else if (type.matches(BirthdayType.Today)) {
                BirthdayUi.Today(id, name,
                    turns = dateDifference.difference(now, date).toString(),
                    date = dateTextMapper.format(date),
                    until = untilTextMapper.format(date)
                )
            }
            else if (type.matches(BirthdayType.Header)) {
                BirthdayUi.Header(name)
            }
            else if (type.matches(BirthdayType.Error)) {
                BirthdayUi.Header("Error")
            }
            else throw IllegalStateException()
    }
}