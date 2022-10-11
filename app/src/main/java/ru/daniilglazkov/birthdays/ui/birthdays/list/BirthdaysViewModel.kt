package ru.daniilglazkov.birthdays.ui.birthdays.list

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import ru.daniilglazkov.birthdays.R
import ru.daniilglazkov.birthdays.domain.birthdaylist.BirthdayListDomain
import ru.daniilglazkov.birthdays.domain.birthdaylist.BirthdayListInteractor
import ru.daniilglazkov.birthdays.ui.birthdays.BirthdayListDomainToUiMapper
import ru.daniilglazkov.birthdays.ui.birthdays.list.chips.*
import ru.daniilglazkov.birthdays.ui.birthdays.list.recyclerstate.RecyclerState
import ru.daniilglazkov.birthdays.ui.birthdays.list.recyclerstate.RecyclerStateCommunication
import ru.daniilglazkov.birthdays.ui.newbirthday.NewBirthdayScreen
import ru.daniilglazkov.birthdays.ui.settings.SettingsScreen
import ru.daniilglazkov.birthdays.ui.core.Fetch
import ru.daniilglazkov.birthdays.ui.core.Init
import ru.daniilglazkov.birthdays.ui.core.navigation.Navigation
import ru.daniilglazkov.birthdays.ui.main.BaseSheetViewModel

/**
 * @author Danil Glazkov on 10.06.2022, 01:22
 */
interface BirthdaysViewModel : BirthdaysNavigation, Fetch, Init,
    RecyclerStateCommunication.Observe,
    BirthdayChipCommunication.Observe
{
    fun changeSearchQuery(query: CharSequence)
    fun reloadAndFetch()


    class Base(
        private val interactor: BirthdayListInteractor,
        private val birthdaysCommunication: BirthdaysCommunication,
        private val chipCommunication: BirthdayChipCommunication,
        private val recyclerStateCommunication: RecyclerStateCommunication,
        private val queryCommunication: QueryCommunication,
        navigation: Navigation.Mutable,
        private val birthdayListDomainToUi: BirthdayListDomainToUiMapper,
        private val birthdayListDomainToChips: BirthdayListDomainToChipsMapper,
    ) : BaseSheetViewModel<BirthdayListUi>(birthdaysCommunication, navigation),
        BirthdaysViewModel
    {
        private val settingsScreen = SettingsScreen(::reloadAndFetch)
        private val newBirthdayScreen = NewBirthdayScreen(::reloadAndFetch)

        private val handleFailure: (Int) -> Unit = { messageId: Int ->
            birthdaysCommunication.showMessage(messageId)
            recyclerStateCommunication.map(RecyclerState.Disable)
            chipCommunication.clear()
        }
        private val handleNotFound = { handleFailure(R.string.birthday_not_found) }
        private val handleEmptyList = { handleFailure(R.string.list_is_empty) }

        private val handleResult: (BirthdayListDomain) -> Unit = { birthdayListDomain ->
            birthdaysCommunication.map(birthdayListDomain.map(birthdayListDomainToUi))
            chipCommunication.map(birthdayListDomain.map(birthdayListDomainToChips))
            recyclerStateCommunication.changeList(birthdayListDomain)
        }

        override fun fetch() = queryCommunication.executeQuery { query: CharSequence ->
            interactor.birthdays(handleResult, query, handleNotFound, handleEmptyList)
        }
        override fun init(isFirstRun: Boolean) {
            if (isFirstRun) interactor.birthdays(handleResult,
                onEmptyCache = ::showNewBirthdayDialog
            )
        }
        override fun changeSearchQuery(query: CharSequence) = queryCommunication.map(query)

        override fun reloadAndFetch() {
            interactor.reload()
            fetch()
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