package ru.daniilglazkov.birthdays.ui.birthdays.list.recyclerstate

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import ru.daniilglazkov.birthdays.domain.birthdaylist.BirthdayListDomain
import ru.daniilglazkov.birthdays.ui.birthdays.list.scrollup.NeedToScrollUpBirthdayList
import ru.daniilglazkov.birthdays.ui.core.Communication

/**
 * @author Danil Glazkov on 09.09.2022, 04:28
 */
interface RecyclerStateCommunication : Communication.Mutable<RecyclerState> {
    fun changeList(list: BirthdayListDomain)

    class Base(
        private val needToScrollUp: NeedToScrollUpBirthdayList
    ) : Communication.Post<RecyclerState>(),
        RecyclerStateCommunication
    {
        override fun changeList(list: BirthdayListDomain) = this.map(
            RecyclerState.Base(
                scrollUp = needToScrollUp.needToScroll(list),
                nestedScrollEnabled = list.isEmpty().not()
            )
        )
    }

    interface Observe {
        fun observeRecyclerState(owner: LifecycleOwner, observer: Observer<RecyclerState>)
    }
}