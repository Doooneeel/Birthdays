package ru.daniilglazkov.birthdays.ui.birthdays.sheetmenu

import ru.daniilglazkov.birthdays.ui.core.NavigationScreen
import ru.daniilglazkov.birthdays.ui.core.NavigationStrategy

/**
 * @author Danil Glazkov on 21.07.2022, 22:44
 */
class MenuScreen(onClosed: () -> Unit = { }) : NavigationScreen(
    "MenuNavigationScreen",
    MenuSheetFragment::class.java,
    NavigationStrategy.Sheet(onClosed)
)