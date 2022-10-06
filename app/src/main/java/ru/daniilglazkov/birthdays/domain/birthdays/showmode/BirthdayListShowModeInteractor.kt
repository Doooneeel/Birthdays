package ru.daniilglazkov.birthdays.domain.birthdays.showmode

import ru.daniilglazkov.birthdays.core.Read
import ru.daniilglazkov.birthdays.domain.birthdays.showmode.sort.SortMode

/**
 * @author Danil Glazkov on 04.08.2022, 04:49
 */
interface BirthdayListShowModeInteractor : Read<ShowModeDomain>, ChangeShowMode {
    fun saveToCache()

    abstract class Abstract(
        private val repository: BirthdayListShowModeRepository,
    ) : BirthdayListShowModeInteractor {
        private var newShowMode: ShowModeDomain = ShowModeDomain.Default()

        protected fun updateSortMode(showMode: (ShowModeDomain) -> ShowModeDomain): ShowModeDomain {
            return showMode(newShowMode).also { changedShowMode ->
                newShowMode = changedShowMode
            }
        }
        override fun read(): ShowModeDomain = repository.read(newShowMode)

        override fun saveToCache() = repository.save(newShowMode)
    }

    class Base(repository: BirthdayListShowModeRepository) : Abstract(repository) {
        override fun changeSortMode(sort: SortMode) = updateSortMode {
            it.changeSortMode(sort)
        }
        override fun changeAdditionalSettings(reverse: Boolean, group: Boolean) = updateSortMode {
            it.changeAdditionalSettings(reverse, group)
        }
    }
}