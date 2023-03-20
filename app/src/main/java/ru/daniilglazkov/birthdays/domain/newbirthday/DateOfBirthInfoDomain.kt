package ru.daniilglazkov.birthdays.domain.newbirthday

/**
 * @author Danil Glazkov on 10.10.2022, 16:13
 */
interface DateOfBirthInfoDomain {

    fun <T> map(mapper: Mapper<T>): T

    interface Mapper<T> {
        fun map(turnsYearsOld: Int, daysToBirthday: Int): T
    }


    data class Base(
        private val turnsYearsOld: Int,
        private val daysToBirthday: Int,
    ) : DateOfBirthInfoDomain {
        override fun <T> map(mapper: Mapper<T>): T =
            mapper.map(turnsYearsOld, daysToBirthday)
    }
}