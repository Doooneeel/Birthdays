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
import ru.daniilglazkov.birthdays.ui.birthdays.list.scrollup.ScrollUp
import ru.daniilglazkov.birthdays.ui.birthdays.list.scrollup.ScrollUpCommunication
import ru.daniilglazkov.birthdays.ui.birthdays.newbirthday.NewBirthdayScreen
import ru.daniilglazkov.birthdays.ui.birthdays.sheetmenu.MenuScreen
import ru.daniilglazkov.birthdays.ui.birthdays.showmode.BirthdaysChips
import ru.daniilglazkov.birthdays.ui.core.Fetch
import ru.daniilglazkov.birthdays.ui.core.Init
import ru.daniilglazkov.birthdays.ui.core.Navigation
import ru.daniilglazkov.birthdays.ui.main.BaseSheetViewModel

/**
 * @author Danil Glazkov on 10.06.2022, 01:22
 */

interface BirthdaysViewModel : Fetch, Init {

    fun changeSearchQuery(text: CharSequence)
    fun showMenu()
    fun showNewBirthdayDialog()
    fun observeChips(owner: LifecycleOwner, observer: Observer<BirthdaysChips>)
    fun observeScrollUp(owner: LifecycleOwner, observer: Observer<ScrollUp>)


    class Base(
        private val interactor: BirthdayListInteractor,
        private val communication: BirthdaysCommunication,
        private val chipCommunication: BirthdayChipCommunication,
        private val scrollUpCommunication: ScrollUpCommunication,
        navigation: Navigation.Mutable,
        provideString: ProvideString,
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
            scrollUpCommunication.changeList(birthdayListDomain)
        }
        override fun init(isFirstRun: Boolean) {
            if (isFirstRun) interactor.handleEmpty {
                navigate(NewBirthdayScreen(onClosed = ::fetch, cancelable = false))
            }
        }
        override fun fetch() = interactor.birthdays(handleResult, query, handleNotFound)

        override fun changeSearchQuery(text: CharSequence) {
            query = text
        }
        override fun showMenu() = navigate(menuScreen)
        override fun showNewBirthdayDialog() = navigate(newBirthdayScreen)

        override fun observeChips(owner: LifecycleOwner, observer: Observer<BirthdaysChips>) {
            chipCommunication.observe(owner, observer)
        }
        override fun observeScrollUp(owner: LifecycleOwner, observer: Observer<ScrollUp>) {
            scrollUpCommunication.observe(owner, observer)
        }
    }
}