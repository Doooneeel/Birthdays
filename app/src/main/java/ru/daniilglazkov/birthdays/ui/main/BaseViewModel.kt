package ru.daniilglazkov.birthdays.ui.main

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import ru.daniilglazkov.birthdays.ui.core.Communication
import ru.daniilglazkov.birthdays.ui.core.navigation.Navigation
import ru.daniilglazkov.birthdays.ui.core.navigation.NavigationScreen

/**
 * @author Danil Glazkov on 10.06.2022, 01:11
 */
interface BaseViewModel<VT> : Communication.Observe<VT> {

    fun navigate(screen: NavigationScreen)
    fun observeNavigation(owner: LifecycleOwner, observer: Observer<NavigationScreen>)


    abstract class Abstract<VT : Any>(
        private val communication: Communication.Mutable<VT>,
        private val navigation: Navigation.Mutable = Navigation.Unit()
    ) : ViewModel(), BaseViewModel<VT> {

        override fun observe(owner: LifecycleOwner, observer: Observer<VT>) =
            communication.observe(owner, observer)

        override fun navigate(screen: NavigationScreen) = navigation.map(screen)

        override fun observeNavigation(owner: LifecycleOwner, observer: Observer<NavigationScreen>) =
            navigation.observe(owner, observer)
    }
}