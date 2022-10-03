package ru.daniilglazkov.birthdays.ui.main

import ru.daniilglazkov.birthdays.ui.birthdays.list.BirthdaysScreen
import ru.daniilglazkov.birthdays.ui.core.Communication
import ru.daniilglazkov.birthdays.ui.core.Init
import ru.daniilglazkov.birthdays.ui.core.Navigation

/**
 * @author Danil Glazkov on 10.06.2022, 21:55
 */
interface MainViewModel : Init {

    class Base(
        navigation: Navigation.Mutable
    ) : BaseViewModel<Unit>(
        Communication.Unit(),
        navigation
    ) , MainViewModel {
        private val birthdaysScreen = BirthdaysScreen()

        override fun init(isFirstRun: Boolean) {
            if (isFirstRun) { navigate(birthdaysScreen) }
        }
    }
}