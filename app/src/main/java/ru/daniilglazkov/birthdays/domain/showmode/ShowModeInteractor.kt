package ru.daniilglazkov.birthdays.domain.showmode

import ru.daniilglazkov.birthdays.domain.showmode.sort.SortMode

/**
 * @author Danil Glazkov on 04.08.2022, 04:49
 */
interface ShowModeInteractor : FetchShowMode, ChangeShowMode {

    class Base(
        private val repository: ShowModeRepository,
        handleResponse: HandleShowModeRepositoryResponse
    ) : ShowModeInteractor {
        private var showMode = handleResponse.handle {
            repository.readShowMode()
        }
        override fun fetchShowMode(): ShowModeDomain = showMode

        override fun changeSortMode(sort: SortMode) {
            showMode = showMode.changeSortMode(sort)
            repository.saveShowMode(showMode)
        }
        override fun changeAdditionalSettings(reverse: Boolean, group: Boolean) {
            showMode = showMode.changeAdditionalSettings(reverse, group)
            repository.saveShowMode(showMode)
        }
    }
}