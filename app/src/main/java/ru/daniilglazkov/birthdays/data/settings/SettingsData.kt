package ru.daniilglazkov.birthdays.data.settings

/**
 * @author Danil Glazkov on 05.08.2022, 02:53
 */
interface SettingsData {

    fun <T> map(mapper: Mapper<T>): T

    interface Mapper<T> {
        fun map(sortModeId: Int, reverse: Boolean, group: Boolean): T
    }


    data class Base(
        private val sortModeId: Int,
        private val reverse: Boolean,
        private val group: Boolean,
    ) : SettingsData {
        override fun <T> map(mapper: Mapper<T>): T =
            mapper.map(sortModeId, reverse, group)
    }
}