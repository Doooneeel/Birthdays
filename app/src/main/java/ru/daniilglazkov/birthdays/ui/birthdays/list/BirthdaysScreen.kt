package ru.daniilglazkov.birthdays.ui.birthdays.list

import ru.daniilglazkov.birthdays.ui.core.navigation.NavigationScreen
import ru.daniilglazkov.birthdays.ui.core.navigation.NavigationStrategy

/**
 * @author Danil Glazkov on 19.06.2022, 12:50
 */
class BirthdaysScreen(
    strategy: NavigationStrategy<Any?> = NavigationStrategy.Replace
) : NavigationScreen(
    "BirthdaySheetNavigationScreen",
    BirthdaysFragment::class.java,
    strategy
)