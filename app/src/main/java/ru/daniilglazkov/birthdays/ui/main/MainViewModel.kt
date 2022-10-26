package ru.daniilglazkov.birthdays.ui.main

import ru.daniilglazkov.birthdays.ui.birthdaylist.BirthdayListScreen
import ru.daniilglazkov.birthdays.ui.core.Communication
import ru.daniilglazkov.birthdays.ui.core.Init
import ru.daniilglazkov.birthdays.ui.core.navigation.Navigation

/**
 * @author Danil Glazkov on 10.06.2022, 21:55
 */
interface MainViewModel : BaseViewModel<Unit>, Init {

    class Base(
        navigation: Navigation.Mutable
    ) : BaseViewModel.Abstract<Unit>(
        Communication.Unit(),
        navigation
    ) , MainViewModel {
        private val birthdayListScreen = BirthdayListScreen()

        override fun init(isFirstRun: Boolean) {
            if (isFirstRun) { navigate(birthdayListScreen) }
        }
    }
}