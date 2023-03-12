package ru.daniilglazkov.birthdays.ui.birthdaylist

import ru.daniilglazkov.birthdays.domain.birthday.BirthdayDomain
import ru.daniilglazkov.birthdays.domain.birthdaylist.BirthdayListDomain

/**
 * @author Danil Glazkov on 01.09.2022, 21:25
 */
interface BirthdayListDomainToItemsUiMapper : BirthdayListDomain.Mapper<BirthdayItemUiList> {

    class Base(private val mapper: BirthdayDomainToItemUiMapper): BirthdayListDomainToItemsUiMapper {
        override fun map(list: List<BirthdayDomain>) = BirthdayItemUiList.Base(
            list.map { it.map(mapper) }
        )
    }
}