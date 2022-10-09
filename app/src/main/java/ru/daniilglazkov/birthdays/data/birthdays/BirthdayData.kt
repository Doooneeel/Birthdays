package ru.daniilglazkov.birthdays.data.birthdays

import java.time.LocalDate

/**
 * @author Danil Glazkov on 10.06.2022, 00:56
 */
interface BirthdayData {
    fun <T> map(mapper: Mapper<T>): T

    class Base(
        private val id: Int,
        private val name: String,
        private val date: LocalDate
    ) : BirthdayData {
        override fun <T> map(mapper: Mapper<T>): T =
            mapper.map(id, name, date)
    }

    interface Mapper<T> {
        fun map(id: Int, name: String, date: LocalDate): T
    }
}