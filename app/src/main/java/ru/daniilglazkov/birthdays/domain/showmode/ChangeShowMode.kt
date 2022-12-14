package ru.daniilglazkov.birthdays.domain.showmode

import ru.daniilglazkov.birthdays.domain.showmode.sort.SortMode

/**
 * @author Danil Glazkov on 10.09.2022, 20:13
 */
interface ChangeShowMode {
    fun changeSortMode(sort: SortMode)
    fun changeAdditionalSettings(reverse: Boolean, group: Boolean)
}

interface ChangeAndReturnShowMode {
    fun changeSortMode(sort: SortMode): ShowModeDomain
    fun changeAdditionalSettings(reverse: Boolean, group: Boolean): ShowModeDomain
}