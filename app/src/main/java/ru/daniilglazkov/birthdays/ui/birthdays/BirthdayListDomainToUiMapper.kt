package ru.daniilglazkov.birthdays.ui.birthdays

import ru.daniilglazkov.birthdays.domain.birthday.BirthdayDomain
import ru.daniilglazkov.birthdays.domain.birthdaylist.BirthdayListDomain
import ru.daniilglazkov.birthdays.ui.birthdays.list.BirthdayListUi

/**
 * @author Danil Glazkov on 01.09.2022, 21:25
 */
interface BirthdayListDomainToUiMapper : BirthdayListDomain.Mapper<BirthdayListUi> {

    class Base(private val mapper: BirthdayDomainToUiMapper): BirthdayListDomainToUiMapper {
        override fun map(list: List<BirthdayDomain>) = BirthdayListUi.Base(
            list.map { birthdayDomain: BirthdayDomain ->
                birthdayDomain.map(mapper)
            }
        )
    }
}
