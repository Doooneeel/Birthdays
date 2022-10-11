package ru.daniilglazkov.birthdays.ui.core.navigation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import ru.daniilglazkov.birthdays.ui.main.BaseSheetFragment

/**
 * @author Danil Glazkov on 19.06.2022, 12:42
 */
abstract class NavigationStrategy<T> {
    abstract fun show(
        id: String, clazz: Class<out Fragment>, containerId: Int, manager: FragmentManager
    )

    class Sheet(private val onClosed: () -> Unit = { }) : NavigationStrategy<Any?>() {
        override fun show(
            id: String,
            clazz: Class<out Fragment>,
            containerId: Int,
            manager: FragmentManager,
        ) {
            val fragment = (clazz.newInstance() as BaseSheetFragment<*, *>).also {
                it.onClosed(onClosed)
            }
            manager.beginTransaction()
                .add(fragment, clazz.simpleName)
                .commit()
        }
    }

    object Add : NavigationStrategy<Any?>() {
        override fun show(
            id: String,
            clazz: Class<out Fragment>,
            containerId: Int,
            manager: FragmentManager
        ) {
            manager.beginTransaction()
                .add(containerId, clazz.newInstance())
                .addToBackStack(id)
                .commit()
        }
    }

    object Replace : NavigationStrategy<Any?>() {
        override fun show(
            id: String,
            clazz: Class<out Fragment>,
            containerId: Int,
            manager: FragmentManager
        ) {
            manager.beginTransaction()
                .replace(containerId, clazz.newInstance())
                .commit()
        }
    }

    object Popup : NavigationStrategy<Any?>() {
        override fun show(
            id: String,
            clazz: Class<out Fragment>,
            containerId: Int,
            manager: FragmentManager
        ) = manager.popBackStack()
    }
}