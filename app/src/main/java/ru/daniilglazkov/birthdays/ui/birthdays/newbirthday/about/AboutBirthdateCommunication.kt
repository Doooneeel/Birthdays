package ru.daniilglazkov.birthdays.ui.birthdays.newbirthday.about

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import ru.daniilglazkov.birthdays.R
import ru.daniilglazkov.birthdays.core.resources.ProvideString
import ru.daniilglazkov.birthdays.ui.core.Communication

/**
 * @author Danil Glazkov on 29.09.2022, 23:08
 */
interface AboutBirthdateCommunication : Communication.Mutable<AboutBirthdate> {
    fun makeNew(age: Int, daysToBirthday: Int)

    class Base(
        private val resources: ProvideString
    ) : Communication.UiUpdate<AboutBirthdate>(), AboutBirthdateCommunication {
        override fun makeNew(age: Int, daysToBirthday: Int) = map(AboutBirthdate.Base(
            resources.quantityString(R.plurals.age, age),
            resources.quantityString(R.plurals.day, daysToBirthday),
        ))
    }

    interface Observe {
        fun observeAboutBirthday(owner: LifecycleOwner, observer: Observer<AboutBirthdate>)
    }
}