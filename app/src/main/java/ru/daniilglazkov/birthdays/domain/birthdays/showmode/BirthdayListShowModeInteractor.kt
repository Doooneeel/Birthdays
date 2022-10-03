package ru.daniilglazkov.birthdays.domain.birthdays.showmode

import ru.daniilglazkov.birthdays.core.Read
import ru.daniilglazkov.birthdays.domain.birthdays.showmode.sort.SortMode

/**
 * @author Danil Glazkov on 04.08.2022, 04:49
 */
interface BirthdayListShowModeInteractor : Read<ShowModeDomain>, ChangeShowMode {

    class Base(
        private val repository: BirthdayListShowModeRepository,
    ) : BirthdayListShowModeInteractor {
        private val defaultShowMode = ShowModeDomain.Default()
        private val lastSavedShowMode get() = repository.read(defaultShowMode)

        override fun changeSortMode(sort: SortMode): ShowModeDomain =
            lastSavedShowMode
                .changeSortMode(sort)
                .also(repository::save)

        override fun changeAdditionalSettings(reverse: Boolean, group: Boolean): ShowModeDomain =
            lastSavedShowMode
                .changeAdditionalSettings(reverse, group)
                .also(repository::save)

        override fun read(): ShowModeDomain = lastSavedShowMode
    }
}