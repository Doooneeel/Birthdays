package ru.daniilglazkov.birthdays.domain.birthdays.showmode

import ru.daniilglazkov.birthdays.domain.birthdays.showmode.sort.SortMode

/**
 * @author Danil Glazkov on 04.08.2022, 04:49
 */
interface BirthdayListShowModeInteractor : FetchShowMode, ChangeShowMode {

    class Base(
        private val repository: BirthdayListShowModeRepository,
    ) : BirthdayListShowModeInteractor {
        private var showMode = repository.read(ShowModeDomain.Default)

        override fun fetchShowMode(): ShowModeDomain = showMode

        override fun changeSortMode(sort: SortMode) {
            showMode = showMode.changeSortMode(sort)
            repository.save(showMode)
        }
        override fun changeAdditionalSettings(reverse: Boolean, group: Boolean) {
            showMode = showMode.changeAdditionalSettings(reverse, group)
            repository.save(showMode)
        }
    }
}

interface FetchShowMode {
    fun fetchShowMode(): ShowModeDomain
}