package ru.daniilglazkov.birthdays.ui.settings

import ru.daniilglazkov.birthdays.ui.core.navigation.NavigationScreen
import ru.daniilglazkov.birthdays.ui.core.navigation.NavigationStrategy

/**
 * @author Danil Glazkov on 21.07.2022, 22:44
 */
class SettingsScreen(onClosed: () -> Unit = { }) : NavigationScreen(
    "SettingsNavigationScreen",
    SettingsSheetFragment::class.java,
    NavigationStrategy.Sheet(onClosed)
)