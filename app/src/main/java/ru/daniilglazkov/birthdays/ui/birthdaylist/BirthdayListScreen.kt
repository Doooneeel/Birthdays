package ru.daniilglazkov.birthdays.ui.birthdaylist

import ru.daniilglazkov.birthdays.ui.core.navigation.NavigationScreen
import ru.daniilglazkov.birthdays.ui.core.navigation.NavigationStrategy

/**
 * @author Danil Glazkov on 19.06.2022, 12:50
 */
data class BirthdayListScreen(
    private val strategy: NavigationStrategy = NavigationStrategy.Replace
) : NavigationScreen.Abstract(
    tag = "BirthdaySheetNavigationScreen",
    BirthdayListFragment::class.java,
    strategy
)