package ru.daniilglazkov.birthdays.ui.settings

import ru.daniilglazkov.birthdays.domain.showmode.*
import ru.daniilglazkov.birthdays.domain.showmode.sort.SortMode
import ru.daniilglazkov.birthdays.ui.settings.showmode.*
import ru.daniilglazkov.birthdays.ui.core.Fetch
import ru.daniilglazkov.birthdays.ui.main.BaseSheetViewModel

/**
 * @author Danil Glazkov on 21.07.2022, 22:33
 */
interface SettingsViewModel : BaseSheetViewModel<ShowModeUi>, Fetch, ChangeShowMode {

    class Base(
        private val interactor: ShowModeInteractor,
        private val communication: ShowModeCommunication,
        private val showModeDomainToUiMapper: ShowModeDomainToUiMapper
    ) : BaseSheetViewModel.Abstract<ShowModeUi>(communication), SettingsViewModel {

        override fun fetch() = interactor.fetchShowMode().let { result: ShowModeDomain ->
            communication.map(result.map(showModeDomainToUiMapper))
        }
        override fun changeSortMode(sort: SortMode) = interactor.changeSortMode(sort)

        override fun changeAdditionalSettings(reverse: Boolean, group: Boolean) =
            interactor.changeAdditionalSettings(reverse, group)
    }
}