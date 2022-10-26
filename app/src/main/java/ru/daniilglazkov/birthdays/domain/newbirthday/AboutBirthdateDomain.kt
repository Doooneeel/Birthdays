package ru.daniilglazkov.birthdays.domain.newbirthday

/**
 * @author Danil Glazkov on 10.10.2022, 16:13
 */
interface AboutBirthdateDomain {
    fun <T> map(mapper: Mapper<T>): T

    class Base(
        private val turnsYearsOld: Int,
        private val daysToBirthday: Int,
    ) : AboutBirthdateDomain {
        override fun <T> map(mapper: Mapper<T>): T =
            mapper.map(turnsYearsOld, daysToBirthday)
    }

    interface Mapper<T> {
        fun map(turnsYearsOld: Int, daysToBirthday: Int): T
    }
}