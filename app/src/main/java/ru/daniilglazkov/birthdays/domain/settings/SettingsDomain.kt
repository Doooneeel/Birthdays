package ru.daniilglazkov.birthdays.domain.settings

import ru.daniilglazkov.birthdays.domain.birthdaylist.transform.sort.SortMode

/**
 * @author Danil Glazkov on 04.08.2022, 03:03
 */
interface SettingsDomain {

    fun <T> map(mapper: Mapper<T>): T

    fun changeSortMode(sort: SortMode): SettingsDomain

    fun change(reverse: Boolean, group: Boolean): SettingsDomain


    interface Mapper<T> {
        fun map(sort: SortMode, reverse: Boolean, group: Boolean): T
    }


    abstract class Abstract(
        private val sort: SortMode,
        private val reverse: Boolean,
        private val group: Boolean,
    ) : SettingsDomain {

        override fun <T> map(mapper: Mapper<T>): T = mapper.map(sort, reverse, group)
        
        override fun change(reverse: Boolean, group: Boolean) = Base(sort, reverse, group)

        override fun changeSortMode(sort: SortMode) = Base(sort, reverse, group)
    }

    data class Base(
        private val sort: SortMode,
        private val reverse: Boolean,
        private val group: Boolean,
    ) : Abstract(sort, reverse, group)

    object Default : Abstract(SortMode.DATE, reverse = false, group = true)
}