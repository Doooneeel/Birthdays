package ru.daniilglazkov.birthdays.ui.core

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer

/**
 * @author Danil Glazkov on 25.08.2022, 14:38
 */
interface ErrorCommunication : Communication.Mutable<ErrorMessage>{

    interface Put {
        fun putError(message: ErrorMessage)
    }

    interface Hide {
        fun hideErrorMessage()
    }

    interface Combo : Put, Hide

    interface Observe {
        fun observeError(owner: LifecycleOwner, observer: Observer<ErrorMessage>)
    }

    class Base : Communication.Ui<ErrorMessage>(), ErrorCommunication
}