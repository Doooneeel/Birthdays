package ru.daniilglazkov.birthdays.domain.newbirthday

import ru.daniilglazkov.birthdays.domain.birthday.BirthdayDomain
import java.time.LocalDate

/**
 * @author Danil Glazkov on 14.08.2022, 23:04
 */
interface NewBirthdayDomain {

    fun <T> map(mapper: Mapper<T>): T

    fun create(id: Int): BirthdayDomain

    interface Mapper<T> {
        fun map(name: String, date: LocalDate): T
    }


    abstract class Abstract(
        private val name: String,
        private val date: LocalDate,
    ) : NewBirthdayDomain {

        override fun <T> map(mapper: Mapper<T>): T = mapper.map(name, date)

        override fun create(id: Int) = BirthdayDomain.Base(id, name, date)
    }

    data class Base(private val name: String, private val date: LocalDate) : Abstract(name, date)

    data class Empty(private val date: LocalDate) : Abstract(name = "", date)
}