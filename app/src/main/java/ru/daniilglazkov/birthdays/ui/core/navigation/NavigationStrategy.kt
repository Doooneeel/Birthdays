package ru.daniilglazkov.birthdays.ui.core.navigation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import ru.daniilglazkov.birthdays.ui.main.BaseSheetFragment

/**
 * @author Danil Glazkov on 19.06.2022, 12:42
 */
abstract class NavigationStrategy {

    abstract fun show(
        id: String,
        clazz: Class<out Fragment>,
        containerId: Int,
        manager: FragmentManager
    )


    class Sheet(private val onClosed: () -> Unit = { }) : NavigationStrategy() {
        override fun show(
            id: String,
            clazz: Class<out Fragment>,
            containerId: Int,
            manager: FragmentManager,
        ) {
            val sheetFragment = clazz.newInstance() as? BaseSheetFragment<*, *>

            if (sheetFragment != null) {
                sheetFragment.onClosed(onClosed)
                sheetFragment.show(manager, id)
            }
        }
    }

    object Add : NavigationStrategy() {
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

    object Replace : NavigationStrategy() {
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
}