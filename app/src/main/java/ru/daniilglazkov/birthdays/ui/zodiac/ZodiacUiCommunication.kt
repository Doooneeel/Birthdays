package ru.daniilglazkov.birthdays.ui.zodiac

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import ru.daniilglazkov.birthdays.ui.core.Communication

/**
 * @author Danil Glazkov on 23.10.2022, 19:41
 */
interface ZodiacUiCommunication : Communication.Mutable<ZodiacUi> {
    class Base : Communication.Ui<ZodiacUi>(), ZodiacUiCommunication

    interface Observe {
        fun observeZodiacUi(owner: LifecycleOwner, observer: Observer<ZodiacUi>)
    }
}