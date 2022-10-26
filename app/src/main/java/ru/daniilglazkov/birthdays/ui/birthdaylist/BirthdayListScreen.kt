package ru.daniilglazkov.birthdays.ui.birthdaylist

import ru.daniilglazkov.birthdays.ui.core.navigation.NavigationScreen
import ru.daniilglazkov.birthdays.ui.core.navigation.NavigationStrategy

/**
 * @author Danil Glazkov on 19.06.2022, 12:50
 */
class BirthdayListScreen(
    strategy: NavigationStrategy<Any?> = NavigationStrategy.Replace
) : NavigationScreen(
    "BirthdaySheetNavigationScreen",
    BirthdayListFragment::class.java,
    strategy
)