package ru.daniilglazkov.birthdays.data.newbirthday

import java.time.LocalDate

/**
 * @author Danil Glazkov on 06.09.2022, 01:12
 */
interface NewBirthdayData {
    fun <T> map(mapper: Mapper<T>): T

    class Base(
        private val name: String,
        private val date: LocalDate
    ) : NewBirthdayData {
        override fun <T> map(mapper: Mapper<T>): T =
            mapper.map(name, date)
    }

    interface Mapper<T> {
        fun map(name: String, date: LocalDate): T
    }
}