package ru.daniilglazkov.birthdays.ui.birthdays.newbirthday

import ru.daniilglazkov.birthdays.ui.core.NavigationScreen
import ru.daniilglazkov.birthdays.ui.core.NavigationStrategy

/**
 * @author Danil Glazkov on 19.06.2022, 13:40
 */
class NewBirthdayScreen(
    onClosed: () -> Unit = { },
    cancelable: Boolean = true
) : NavigationScreen(
    "NewBirthdaySheetNavigationScreen",
    NewBirthdaySheetFragment::class.java,
    NavigationStrategy.Sheet(onClosed, cancelable)
)
