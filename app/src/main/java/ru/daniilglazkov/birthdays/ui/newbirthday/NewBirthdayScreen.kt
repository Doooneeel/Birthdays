package ru.daniilglazkov.birthdays.ui.newbirthday

import ru.daniilglazkov.birthdays.ui.core.navigation.NavigationScreen
import ru.daniilglazkov.birthdays.ui.core.navigation.NavigationStrategy

/**
 * @author Danil Glazkov on 19.06.2022, 13:40
 */
class NewBirthdayScreen(
    onClosed: () -> Unit = { },
) : NavigationScreen(
    "NewBirthdaySheetNavigationScreen",
    NewBirthdaySheetFragment::class.java,
    NavigationStrategy.Sheet(onClosed)
)
