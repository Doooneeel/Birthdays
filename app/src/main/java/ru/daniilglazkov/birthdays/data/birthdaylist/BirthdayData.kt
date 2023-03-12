package ru.daniilglazkov.birthdays.data.birthdaylist

/**
 * @author Danil Glazkov on 10.06.2022, 00:56
 */
interface BirthdayData {

    fun <T> map(mapper: Mapper<T>): T

    interface Mapper<T> {
        fun map(id: Int, name: String, epochDay: Long): T
    }


    data class Base(
        private val id: Int,
        private val name: String,
        private val epochDay: Long
    ) : BirthdayData {
        override fun <T> map(mapper: Mapper<T>): T =
            mapper.map(id, name, epochDay)
    }
}