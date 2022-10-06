package ru.daniilglazkov.birthdays.ui.birthdays.settings

import ru.daniilglazkov.birthdays.domain.birthdays.showmode.BirthdayListShowModeInteractor
import ru.daniilglazkov.birthdays.domain.birthdays.showmode.ShowModeDomain
import ru.daniilglazkov.birthdays.domain.birthdays.showmode.sort.SortMode
import ru.daniilglazkov.birthdays.ui.birthdays.showmode.ShowModeCommunication
import ru.daniilglazkov.birthdays.ui.birthdays.showmode.ShowModeDomainToUiMapper
import ru.daniilglazkov.birthdays.ui.birthdays.showmode.ShowModeUi
import ru.daniilglazkov.birthdays.ui.core.Completion
import ru.daniilglazkov.birthdays.ui.core.Init
import ru.daniilglazkov.birthdays.ui.main.BaseSheetViewModel

/**
 * @author Danil Glazkov on 21.07.2022, 22:33
 */
interface SettingsViewModel : Init, Completion {

    fun changeSortMode(sort: SortMode)
    fun changeAdditionalSettings(reverse: Boolean, group: Boolean)

    class Base(
        private val interactor: BirthdayListShowModeInteractor,
        private val communication: ShowModeCommunication,
        private val showModeDomainToUiMapper: ShowModeDomainToUiMapper
    ) : BaseSheetViewModel<ShowModeUi>(communication),
        SettingsViewModel
    {
        private val change = { showModeDomain: ShowModeDomain ->
            communication.map(showModeDomain.map(showModeDomainToUiMapper))
        }
        override fun init(isFirstRun: Boolean) {
            if (isFirstRun) change(interactor.read())
        }
        override fun changeSortMode(sort: SortMode) {
            change(interactor.changeSortMode(sort))
        }
        override fun changeAdditionalSettings(reverse: Boolean, group: Boolean) {
            change(interactor.changeAdditionalSettings(reverse, group))
        }
        override fun complete() = interactor.saveToCache()
    }
}