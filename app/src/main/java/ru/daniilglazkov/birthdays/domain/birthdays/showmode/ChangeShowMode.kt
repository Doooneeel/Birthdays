package ru.daniilglazkov.birthdays.domain.birthdays.showmode

import ru.daniilglazkov.birthdays.domain.birthdays.showmode.sort.SortMode

/**
 * @author Danil Glazkov on 10.09.2022, 20:13
 */
interface ChangeShowMode {
    fun changeSortMode(sort: SortMode): ShowModeDomain
    fun changeAdditionalSettings(reverse: Boolean, group: Boolean): ShowModeDomain
}