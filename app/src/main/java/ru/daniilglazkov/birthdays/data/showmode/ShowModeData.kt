package ru.daniilglazkov.birthdays.data.showmode

import ru.daniilglazkov.birthdays.domain.birthdays.showmode.ShowModeDomain
import ru.daniilglazkov.birthdays.domain.birthdays.showmode.sort.SortMode

/**
 * @author Danil Glazkov on 05.08.2022, 02:53
 */
interface ShowModeData {
    fun <T> map(mapper: Mapper<T>): T

    class Base(
        private val sort: SortMode,
        private val reverse: Boolean,
        private val group: Boolean,
    ) : ShowModeData {
        override fun <T> map(mapper: Mapper<T>): T = mapper.map(sort, reverse, group)
    }

    interface Mapper<T> {
        fun map(sort: SortMode, reverse: Boolean, group: Boolean): T

        abstract class Abstract<T>(
            private val value: (SortMode, Boolean, Boolean) -> T,
        ) : Mapper<T> {
            override fun map(sort: SortMode, reverse: Boolean, group: Boolean): T =
                value.invoke(sort, reverse, group)
        }
        class ToDomain : Abstract<ShowModeDomain>(ShowModeDomain::Base)
        class ToDatabaseModel : Abstract<ShowModeEntity>(::ShowModeEntity)
    }
}