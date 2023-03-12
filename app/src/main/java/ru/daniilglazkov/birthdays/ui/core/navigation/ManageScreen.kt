package ru.daniilglazkov.birthdays.ui.core.navigation

import androidx.fragment.app.FragmentManager

/**
 * @author Danil Glazkov on 19.06.2022, 12:26
 */
interface ManageScreen {

    fun show(screen: NavigationScreen)


    class Base(private val container: Int, private val manager: FragmentManager) : ManageScreen {
        override fun show(screen: NavigationScreen) = screen.show(container, manager)
    }
}