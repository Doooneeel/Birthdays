package ru.daniilglazkov.birthdays.domain.birthdays.showmode

import ru.daniilglazkov.birthdays.domain.birthdays.showmode.sort.SortMode

/**
 * @author Danil Glazkov on 04.08.2022, 03:03
 */
interface ShowModeDomain : ChangeShowMode {
    fun <T> map(mapper: Mapper<T>): T

    abstract class Abstract(
        private val sort: SortMode,
        private val reverse: Boolean,
        private val group: Boolean,
    ) : ShowModeDomain {
        override fun <T> map(mapper: Mapper<T>): T = mapper.map(sort, reverse, group)
        override fun changeAdditionalSettings(reverse: Boolean, group: Boolean) = Base(sort, reverse, group)
        override fun changeSortMode(sort: SortMode) = Base(sort, reverse, group)
    }

    class Base(sort: SortMode, reverse: Boolean, group: Boolean) : Abstract(sort, reverse, group)

    class Default : Abstract(SortMode.DATE, reverse = false, group = true)

    interface Mapper<T> {
        fun map(sort: SortMode, reverse: Boolean, group: Boolean): T

        abstract class Abstract<T>(private val value: (SortMode, Boolean, Boolean) -> T) : Mapper<T> {
            override fun map(sort: SortMode, reverse: Boolean, group: Boolean): T =
                value.invoke(sort, reverse, group)
        }

        class Transform(
            private val factory: TransformBirthdayListFactory,
        ) : Mapper<TransformBirthdayList> {
            override fun map(sort: SortMode, reverse: Boolean, group: Boolean) =
                factory.create(sort, reverse, group)
        }
    }
}