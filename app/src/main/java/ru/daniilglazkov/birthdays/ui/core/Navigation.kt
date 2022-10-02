package ru.daniilglazkov.birthdays.ui.core

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer

/**
 * @author Danil Glazkov on 19.06.2022, 13:20
 */
interface Navigation {

    interface Update : Communication.Update<NavigationScreen>

    interface Observe : Communication.Observe<NavigationScreen>

    interface Mutable : Update, Observe

    class Base : Communication.SinglePostUpdate<NavigationScreen>(), Mutable

    class Unit : Mutable {
        override fun map(source: NavigationScreen) = Unit
        override fun observe(owner: LifecycleOwner, observer: Observer<NavigationScreen>) = Unit
    }
}