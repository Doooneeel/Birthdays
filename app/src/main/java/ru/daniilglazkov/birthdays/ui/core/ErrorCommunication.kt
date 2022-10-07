package ru.daniilglazkov.birthdays.ui.core

import androidx.annotation.StringRes
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import ru.daniilglazkov.birthdays.core.Clear
import ru.daniilglazkov.birthdays.core.resources.ProvideString

/**
 * @author Danil Glazkov on 25.08.2022, 14:38
 */
interface ErrorCommunication : Communication.Mutable<ErrorMessage>, Clear {
    fun throwErrorMessage(@StringRes id: Int)

    class Base(
        private val provideString: ProvideString,
    ) : Communication.UiUpdate<ErrorMessage>(),
        ErrorCommunication
    {
        override fun throwErrorMessage(id: Int) = map(
            ErrorMessage.Base(provideString.string(id))
        )
        override fun clear() = map(ErrorMessage.Empty)
    }

    interface Observe {
        fun observeError(owner: LifecycleOwner, observer: Observer<ErrorMessage>)
    }

    interface Clear {
        fun clearErrorMessage()
    }

}