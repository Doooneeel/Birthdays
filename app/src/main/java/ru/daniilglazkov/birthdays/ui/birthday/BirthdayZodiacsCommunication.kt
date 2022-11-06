package ru.daniilglazkov.birthdays.ui.birthday

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import ru.daniilglazkov.birthdays.ui.core.Communication

/**
 * @author Danil Glazkov on 02.11.2022, 10:34
 */
interface BirthdayZodiacsCommunication : Communication.Mutable<BirthdayZodiacsUi> {
    class Base : Communication.Ui<BirthdayZodiacsUi>(), BirthdayZodiacsCommunication

    interface Observe {
        fun observeBirthdayZodiacs(owner: LifecycleOwner, observer: Observer<BirthdayZodiacsUi>)
    }
}