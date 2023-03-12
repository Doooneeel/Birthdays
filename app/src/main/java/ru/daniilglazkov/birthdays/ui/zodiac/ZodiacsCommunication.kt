package ru.daniilglazkov.birthdays.ui.zodiac

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import ru.daniilglazkov.birthdays.ui.core.Communication

/**
 * @author Danil Glazkov on 02.11.2022, 10:34
 */
interface ZodiacsCommunication : Communication.Mutable<ZodiacsUi> {

    interface Put {
        fun putZodiacs(zodiacs: ZodiacsUi)
    }

    interface Observe {
        fun observeZodiacs(owner: LifecycleOwner, observer: Observer<ZodiacsUi>)
    }

    class Base : Communication.Ui<ZodiacsUi>(), ZodiacsCommunication
}