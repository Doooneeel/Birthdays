package ru.daniilglazkov.birthdays.domain.birthdays.newbirthday

import ru.daniilglazkov.birthdays.domain.birthdays.BirthdayDomain
import java.time.LocalDate

/**
 * @author Danil Glazkov on 14.08.2022, 23:04
 */
interface NewBirthdayDomain {
    fun <T> map(mapper: Mapper<T>): T

    abstract class Abstract(
        private val name: String,
        private val date: LocalDate,
    ) : NewBirthdayDomain {
        override fun <T> map(mapper: Mapper<T>): T = mapper.map(name, date)
    }

    class Base(name: String, date: LocalDate) : Abstract(name, date)

    class Default(date: LocalDate) : Abstract(name = "", date)

    interface Mapper<T> {
        fun map(name: String, date: LocalDate): T

        abstract class Abstract<T>(private val data: (String, LocalDate) -> T) : Mapper<T> {
            override fun map(name: String, date: LocalDate): T = data.invoke(name, date)
        }

        class Create : Mapper<BirthdayDomain> {
            override fun map(name: String, date: LocalDate): BirthdayDomain =
                BirthdayDomain.Base(/*room generate id*/ id = -1, name, date)
        }
    }

}