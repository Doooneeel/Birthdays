package ru.daniilglazkov.birthdays.domain.newbirthday

import ru.daniilglazkov.birthdays.domain.birthday.BirthdayDomain
import java.time.LocalDate

/**
 * @author Danil Glazkov on 14.08.2022, 23:04
 */
interface NewBirthdayDomain {

    fun <T> map(mapper: Mapper<T>): T
    fun create(): BirthdayDomain


    abstract class Abstract(
        private val name: String,
        private val date: LocalDate,
    ) : NewBirthdayDomain {
        override fun <T> map(mapper: Mapper<T>): T =
            mapper.map(name, date)

        override fun create(): BirthdayDomain =
            BirthdayDomain.Base(/*room generate id*/ id = -1, name, date)
    }

    class Base(name: String, date: LocalDate) : Abstract(name, date)
    class Empty(date: LocalDate) : Abstract(name = "", date)


    interface Mapper<T> {
        fun map(name: String, date: LocalDate): T
    }
}