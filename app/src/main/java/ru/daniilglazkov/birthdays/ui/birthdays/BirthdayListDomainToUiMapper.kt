package ru.daniilglazkov.birthdays.ui.birthdays

import ru.daniilglazkov.birthdays.domain.birthdays.BirthdayDomain
import ru.daniilglazkov.birthdays.domain.birthdays.BirthdayListDomain
import ru.daniilglazkov.birthdays.ui.birthdays.list.BirthdaysUi

/**
 * @author Danil Glazkov on 01.09.2022, 21:25
 */

interface BirthdayListDomainToUiMapper : BirthdayListDomain.Mapper<BirthdaysUi> {

    class Base(private val mapper: BirthdayDomainToUiMapper) : BirthdayListDomainToUiMapper {
        override fun map(list: List<BirthdayDomain>) = BirthdaysUi.Base(
            list.map { it.map(mapper) }
        )
    }
}
