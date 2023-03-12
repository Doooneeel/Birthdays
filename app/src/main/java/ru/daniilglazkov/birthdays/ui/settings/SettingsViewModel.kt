package ru.daniilglazkov.birthdays.ui.settings

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import ru.daniilglazkov.birthdays.domain.settings.*
import ru.daniilglazkov.birthdays.domain.birthdaylist.transform.sort.SortMode
import ru.daniilglazkov.birthdays.ui.core.*
import ru.daniilglazkov.birthdays.ui.main.BaseSheetViewModel

/**
 * @author Danil Glazkov on 21.07.2022, 22:33
 */
interface SettingsViewModel : BaseSheetViewModel, Fetch, SettingsCommunication.Observe, Complete {

    fun changeSettings(reverse: Boolean, group: Boolean)

    fun changeSortMode(sort: SortMode)


    class Base(
        private val interactor: SettingsInteractor,
        private val handleSettings: HandleSettingsChange,
        private val communication: Communication.Observe<SettingsUi>,
        private val dispatchers: CoroutineDispatchers,
        sheetCommunication: SheetCommunication,
    ) : BaseSheetViewModel.Abstract(sheetCommunication), SettingsViewModel {

        override fun fetch() = handleSettings.handle(viewModelScope) {
            interactor.fetchSettings()
        }

        override fun changeSortMode(sort: SortMode) = handleSettings.handle(viewModelScope) {
            interactor.changeSortMode(sort)
        }

        override fun changeSettings(
            reverse: Boolean,
            group: Boolean
        ) = handleSettings.handle(viewModelScope) {
            interactor.change(reverse, group)
        }

        override fun complete() {
            dispatchers.launchBackground(viewModelScope) {
                interactor.saveChanges()
            }
        }

        override fun observeSettings(owner: LifecycleOwner, observer: Observer<SettingsUi>) {
            communication.observe(owner, observer)
        }
    }
}