package ru.daniilglazkov.birthdays.ui.birthdays.list

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import ru.daniilglazkov.birthdays.R
import ru.daniilglazkov.birthdays.core.resources.ProvideString
import ru.daniilglazkov.birthdays.domain.birthdays.BirthdayListDomain
import ru.daniilglazkov.birthdays.domain.birthdays.BirthdayListInteractor
import ru.daniilglazkov.birthdays.ui.birthdays.list.chips.BirthdayListDomainToChipsMapper
import ru.daniilglazkov.birthdays.ui.birthdays.BirthdayListDomainToUiMapper
import ru.daniilglazkov.birthdays.ui.birthdays.BirthdayUi
import ru.daniilglazkov.birthdays.ui.birthdays.list.chips.BirthdayChipCommunication
import ru.daniilglazkov.birthdays.ui.birthdays.list.recyclerstate.RecyclerStateCommunication
import ru.daniilglazkov.birthdays.ui.birthdays.list.recyclerstate.RecyclerState
import ru.daniilglazkov.birthdays.ui.birthdays.newbirthday.NewBirthdayScreen
import ru.daniilglazkov.birthdays.ui.birthdays.settings.SettingsScreen
import ru.daniilglazkov.birthdays.ui.birthdays.list.chips.BirthdayListChips
import ru.daniilglazkov.birthdays.ui.core.Fetch
import ru.daniilglazkov.birthdays.ui.core.Init
import ru.daniilglazkov.birthdays.ui.core.Navigation
import ru.daniilglazkov.birthdays.ui.main.BaseSheetViewModel

/**
 * @author Danil Glazkov on 10.06.2022, 01:22
 */
interface BirthdaysViewModel : BirthdaysNavigation, Fetch, Init {

    fun changeSearchQuery(text: CharSequence)
    fun observeChips(owner: LifecycleOwner, observer: Observer<BirthdayListChips>)
    fun observeRecyclerState(owner: LifecycleOwner, observer: Observer<RecyclerState>)


    class Base(
        private val interactor: BirthdayListInteractor,
        private val communication: BirthdaysCommunication,
        private val chipCommunication: BirthdayChipCommunication,
        private val recyclerStateCommunication: RecyclerStateCommunication,
        navigation: Navigation.Mutable,
        private val provideString: ProvideString,
        private val birthdayListDomainToUi: BirthdayListDomainToUiMapper,
        private val birthdayListDomainToChips: BirthdayListDomainToChipsMapper,
    ) : BaseSheetViewModel<BirthdaysUi>(communication, navigation),
        BirthdaysViewModel
    {
        private var query: CharSequence = ""
        private val settingsScreen = SettingsScreen(onClosed = ::fetch)
        private val newBirthdayScreen = NewBirthdayScreen(onClosed = ::fetch)
        private val notFoundMessage by lazy {
            BirthdayUi.Message(provideString.string(R.string.birthday_not_found))
        }
        private val handleNotFound = {
            communication.map(notFoundMessage)
        }
        private val handleResult: (BirthdayListDomain) -> Unit = { birthdayListDomain ->
            communication.map(birthdayListDomain.map(birthdayListDomainToUi))
            chipCommunication.map(birthdayListDomain.map(birthdayListDomainToChips))
            recyclerStateCommunication.changeList(birthdayListDomain)
        }
        private val handleEmptyList = {
            communication.map(BirthdayUi.Message(provideString.string(R.string.list_is_empty)))
            recyclerStateCommunication.map(RecyclerState.Disable())
            chipCommunication.clear()
        }
        override fun fetch() {
            interactor.birthdays(handleResult, query, handleNotFound, handleEmptyList)
        }
        override fun init(isFirstRun: Boolean) {
            if (isFirstRun) {
                interactor.birthdays(handleResult, onEmptyCache = ::showNewBirthdayDialog)
            }
        }
        override fun changeSearchQuery(text: CharSequence) {
            query = text
        }
        override fun showSettingsDialog() = navigate(settingsScreen)
        override fun showNewBirthdayDialog() = navigate(newBirthdayScreen)

        override fun observeChips(owner: LifecycleOwner, observer: Observer<BirthdayListChips>) {
            chipCommunication.observe(owner, observer)
        }
        override fun observeRecyclerState(owner: LifecycleOwner, observer: Observer<RecyclerState>) {
            recyclerStateCommunication.observe(owner, observer)
        }
    }
}

interface BirthdaysNavigation {
    fun showNewBirthdayDialog()
    fun showSettingsDialog()
}