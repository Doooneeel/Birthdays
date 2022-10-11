package ru.daniilglazkov.birthdays.ui.newbirthday.about

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import ru.daniilglazkov.birthdays.ui.core.Communication

/**
 * @author Danil Glazkov on 29.09.2022, 23:08
 */
interface AboutBirthdateCommunication : Communication.Mutable<AboutBirthdateUi> {

    class Base : Communication.Ui<AboutBirthdateUi>(), AboutBirthdateCommunication


    interface Observe {
        fun observeAboutBirthday(owner: LifecycleOwner, observer: Observer<AboutBirthdateUi>)
    }
}