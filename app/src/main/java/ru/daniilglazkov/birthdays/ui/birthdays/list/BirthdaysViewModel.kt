package ru.daniilglazkov.birthdays.ui.birthdays.list

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import ru.daniilglazkov.birthdays.R
import ru.daniilglazkov.birthdays.core.resources.ProvideString
import ru.daniilglazkov.birthdays.domain.birthdays.BirthdayListDomain
import ru.daniilglazkov.birthdays.domain.birthdays.BirthdayListInteractor
import ru.daniilglazkov.birthdays.ui.birthdays.BirthdayListDomainToChipsMapper
import ru.daniilglazkov.birthdays.ui.birthdays.BirthdayListDomainToUiMapper
import ru.daniilglazkov.birthdays.ui.birthdays.BirthdayUi
import ru.daniilglazkov.birthdays.ui.birthdays.BirthdaysCommunication
import ru.daniilglazkov.birthdays.ui.birthdays.newbirthday.NewBirthdayScreen
import ru.daniilglazkov.birthdays.ui.birthdays.sheetmenu.MenuScreen
import ru.daniilglazkov.birthdays.ui.birthdays.showmode.BirthdaysChips
import ru.daniilglazkov.birthdays.ui.core.Fetch
import ru.daniilglazkov.birthdays.ui.core.Navigation
import ru.daniilglazkov.birthdays.ui.main.BaseSheetViewModel

/**
 * @author Danil Glazkov on 10.06.2022, 01:22
 */
interface BirthdaysViewModel : Fetch {

    fun changeSearchQuery(text: CharSequence)
    fun showMenu()
    fun showNewBirthdayDialog()
    fun observeChips(owner: LifecycleOwner, observer: Observer<BirthdaysChips>)
    fun observeRecyclerState(owner: LifecycleOwner, observer: Observer<RecyclerViewState>)


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
        private val menuScreen = MenuScreen(onClosed = ::fetch)
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
            recyclerStateCommunication.map(RecyclerViewState.Disable())
            chipCommunication.clear()
        }
        override fun fetch() {
            interactor.birthdays(handleResult, query, handleNotFound, handleEmptyList)
        }
        override fun changeSearchQuery(text: CharSequence) {
            query = text
        }
        override fun showMenu() = navigate(menuScreen)
        override fun showNewBirthdayDialog() = navigate(newBirthdayScreen)

        override fun observeChips(owner: LifecycleOwner, observer: Observer<BirthdaysChips>) {
            chipCommunication.observe(owner, observer)
        }
        override fun observeRecyclerState(owner: LifecycleOwner, observer: Observer<RecyclerViewState>) {
            recyclerStateCommunication.observe(owner, observer)
        }
    }
}