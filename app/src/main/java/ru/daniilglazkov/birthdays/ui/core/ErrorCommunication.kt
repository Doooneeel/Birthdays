package ru.daniilglazkov.birthdays.ui.core

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import ru.daniilglazkov.birthdays.core.Clear

/**
 * @author Danil Glazkov on 25.08.2022, 14:38
 */
interface ErrorCommunication : Communication.Mutable<ErrorMessage>, Clear {

    class Base : Communication.UiUpdate<ErrorMessage>(), ErrorCommunication {
        private val emptyErrorMessage by lazy { ErrorMessage.Empty() }
        override fun clear() = map(emptyErrorMessage)
    }

    interface Observe {
        fun observeError(owner: LifecycleOwner, observer: Observer<ErrorMessage>)
    }

}