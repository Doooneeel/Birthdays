package ru.daniilglazkov.birthdays.ui.main

import ru.daniilglazkov.birthdays.ui.birthdays.list.BirthdaysScreen
import ru.daniilglazkov.birthdays.ui.core.Communication
import ru.daniilglazkov.birthdays.ui.core.Navigation

/**
 * @author Danil Glazkov on 10.06.2022, 21:55
 */
class MainViewModel(
    navigation: Navigation.Mutable
) : BaseViewModel<Unit>(
    Communication.Unit(),
    navigation
) {
    private val birthdaysScreen = BirthdaysScreen()

    fun init(firstOpening: Boolean) {

        if (firstOpening) { navigate(birthdaysScreen) }
    }
}
