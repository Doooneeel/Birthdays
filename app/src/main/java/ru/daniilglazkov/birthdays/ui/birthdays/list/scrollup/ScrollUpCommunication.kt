package ru.daniilglazkov.birthdays.ui.birthdays.list.scrollup

import ru.daniilglazkov.birthdays.domain.birthdays.BirthdayListDomain
import ru.daniilglazkov.birthdays.ui.core.Communication

/**
 * @author Danil Glazkov on 09.09.2022, 04:28
 */
interface ScrollUpCommunication : Communication.Mutable<ScrollUp> {
    fun changeList(birthdays: BirthdayListDomain)

    class Base(
        private val needToScrollUp: NeedToScrollUpBirthdayList
    ) : Communication.PostUpdate<ScrollUp>(),
        ScrollUpCommunication
    {
        override fun changeList(birthdays: BirthdayListDomain) = map(
            ScrollUp.Base(needToScrollUp.needToScroll(birthdays))
        )
    }
}