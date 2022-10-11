package ru.daniilglazkov.birthdays.domain.newbirthday

/**
 * @author Danil Glazkov on 10.10.2022, 16:13
 */
interface AboutBirthdateDomain {
    fun <T> map(mapper: Mapper<T>): T

    class Base(
        private val turnedYear: Int,
        private val daysToBirthday: Int,
    ) : AboutBirthdateDomain {
        override fun <T> map(mapper: Mapper<T>): T =
            mapper.map(turnedYear, daysToBirthday)
    }

    interface Mapper<T> {
        fun map(turnedYear: Int, daysToBirthday: Int): T
    }
}