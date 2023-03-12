package ru.daniilglazkov.birthdays.ui.main

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import ru.daniilglazkov.birthdays.ui.core.navigation.Navigation
import ru.daniilglazkov.birthdays.ui.core.navigation.NavigationScreen

/**
 * @author Danil Glazkov on 10.06.2022, 01:11
 */
interface BaseViewModel {

    fun navigate(screen: NavigationScreen)

    fun observeNavigation(owner: LifecycleOwner, observer: Observer<NavigationScreen>)


    abstract class Abstract(
        private val navigation: Navigation.Mutable = Navigation.Unit()
    ) : ViewModel(), BaseViewModel {

        override fun navigate(screen: NavigationScreen) = navigation.put(screen)

        override fun observeNavigation(owner: LifecycleOwner, observer: Observer<NavigationScreen>) =
            navigation.observe(owner, observer)
    }
}