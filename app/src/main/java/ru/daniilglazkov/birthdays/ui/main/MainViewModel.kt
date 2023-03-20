package ru.daniilglazkov.birthdays.ui.main

import ru.daniilglazkov.birthdays.service.core.receivers.ReceiverWrapper
import ru.daniilglazkov.birthdays.ui.birthdaylist.BirthdayListScreen
import ru.daniilglazkov.birthdays.ui.core.Init
import ru.daniilglazkov.birthdays.ui.core.navigation.Navigation

/**
 * @author Danil Glazkov on 10.06.2022, 21:55
 */
interface MainViewModel : BaseViewModel, Init {

    class Base(
        private val workManager: ReceiverWrapper,
        navigation: Navigation.Mutable,
    ) : BaseViewModel.Abstract(navigation), MainViewModel {
        override fun init(isFirstRun: Boolean) {
            if (isFirstRun) {
                navigate(BirthdayListScreen())
                workManager.start()
            }
        }
    }
}