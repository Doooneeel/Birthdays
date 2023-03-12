package ru.daniilglazkov.birthdays.ui.core.navigation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

/**
 * @author Danil Glazkov on 19.06.2022, 12:23
 */
interface NavigationScreen : ShowFragment {

    abstract class Abstract(
        private val tag: String,
        private val clazz: Class<out Fragment>,
        private val strategy: NavigationStrategy
    ) : NavigationScreen {

        override fun show(containerId: Int, fragmentManager: FragmentManager) =
            strategy.show(tag, clazz, containerId, fragmentManager)

        override fun equals(other: Any?): Boolean = (other as? Abstract)?.tag == tag

        override fun hashCode(): Int = tag.hashCode()
    }
}
