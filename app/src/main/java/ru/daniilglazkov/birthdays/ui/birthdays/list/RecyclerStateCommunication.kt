package ru.daniilglazkov.birthdays.ui.birthdays.list

import ru.daniilglazkov.birthdays.domain.birthdays.BirthdayListDomain
import ru.daniilglazkov.birthdays.ui.birthdays.list.scrollup.NeedToScrollUpBirthdayList
import ru.daniilglazkov.birthdays.ui.core.Communication

/**
 * @author Danil Glazkov on 09.09.2022, 04:28
 */
interface RecyclerStateCommunication : Communication.Mutable<RecyclerViewState> {
    fun changeList(list: BirthdayListDomain)

    class Base(
        private val needToScrollUp: NeedToScrollUpBirthdayList
    ) : Communication.PostUpdate<RecyclerViewState>(),
        RecyclerStateCommunication
    {
        override fun changeList(list: BirthdayListDomain) = this.map(RecyclerViewState.Base(
            scrollUp = needToScrollUp.needToScroll(list),
            nestedScrollEnabled = list.isEmpty().not()
        ))
    }
}