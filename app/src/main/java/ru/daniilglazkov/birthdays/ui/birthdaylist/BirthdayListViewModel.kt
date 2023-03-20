package ru.daniilglazkov.birthdays.ui.birthdaylist

import androidx.lifecycle.viewModelScope
import ru.daniilglazkov.birthdays.domain.birthdaylist.BirthdayListInteractor
import ru.daniilglazkov.birthdays.ui.birthdaylist.chips.*
import ru.daniilglazkov.birthdays.ui.core.*
import ru.daniilglazkov.birthdays.ui.core.navigation.Navigation
import ru.daniilglazkov.birthdays.ui.main.BaseViewModel
import ru.daniilglazkov.birthdays.ui.newbirthday.NewBirthdayScreen
import ru.daniilglazkov.birthdays.ui.settings.SettingsScreen

/**
 * @author Danil Glazkov on 10.06.2022, 01:22
 */
interface BirthdayListViewModel : BaseViewModel, BirthdayListCommunications.Observe, Fetch,
    BirthdayListNavigation, Init
{
    fun changeSearchQuery(query: CharSequence)

    fun changePosition(chip: ChipUi)


    class Base(
        private val interactor: BirthdayListInteractor,
        private val communications: BirthdayListCommunications.Mutable,
        private val handleRequest: HandleBirthdayListRequest,
        navigation: Navigation.Mutable,
    ) : BaseViewModel.Abstract(navigation),
        BirthdayListViewModel,
        BirthdayListCommunications.Observe by communications
    {
        private val settingsScreen = SettingsScreen(onClosed = ::fetch)
        private val newBirthdayScreen = NewBirthdayScreen(onClosed = ::fetch)

        override fun init(isFirstRun: Boolean) {
            if (isFirstRun && interactor.firstLaunch()) showNewBirthdayDialog()
        }

        override fun fetch() = handleRequest.handle(viewModelScope) {
            communications.fetchBirthdays(interactor)
        }

        override fun changeSearchQuery(query: CharSequence) = communications.putQuery(query)

        override fun changePosition(chip: ChipUi) = communications.smoothScroll(
            chip.position(interactor)
        )

        override fun showSettingsDialog() = navigate(settingsScreen)

        override fun showNewBirthdayDialog() = navigate(newBirthdayScreen)
    }
}