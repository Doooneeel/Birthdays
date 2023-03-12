package ru.daniilglazkov.birthdays.ui.birthdaylist.recycler.state

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import ru.daniilglazkov.birthdays.ui.core.Communication

/**
 * @author Danil Glazkov on 09.09.2022, 04:28
 */
interface RecyclerStateCommunication : Communication.Mutable<RecyclerState> {

    interface Observe {
        fun observeRecyclerState(owner: LifecycleOwner, observer: Observer<RecyclerState>)
    }

    interface Put {
        fun putRecyclerState(state: RecyclerState)
    }


    class Base : Communication.Post<RecyclerState>(), RecyclerStateCommunication
}