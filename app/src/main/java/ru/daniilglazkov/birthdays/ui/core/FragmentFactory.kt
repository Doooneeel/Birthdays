package ru.daniilglazkov.birthdays.ui.core

import androidx.fragment.app.FragmentManager
import ru.daniilglazkov.birthdays.ui.core.navigation.NavigationScreen

/**
 * @author Danil Glazkov on 19.06.2022, 12:26
 */
interface FragmentFactory {
    fun fragment(screen: NavigationScreen)

    class Base(
        private val containerId: Int,
        private val fragmentManager: FragmentManager,
    ) : FragmentFactory {
        override fun fragment(screen: NavigationScreen) =
            screen.show(containerId, fragmentManager)
    }
}