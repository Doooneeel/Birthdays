package ru.daniilglazkov.birthdays.ui.core.navigation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import ru.daniilglazkov.birthdays.domain.core.Matches
import ru.daniilglazkov.birthdays.ui.core.ShowScreen

/**
 * @author Danil Glazkov on 19.06.2022, 12:23
 */
abstract class NavigationScreen(
    private val id: String,
    private val clazz: Class<out Fragment>,
    private val strategy: NavigationStrategy<Any?>
) : ShowScreen, Matches<NavigationScreen> {
    override fun matches(data: NavigationScreen): Boolean = data.id == id

    override fun show(containerId: Int, fragmentManager: FragmentManager) =
        strategy.show(id, clazz, containerId, fragmentManager)
}
