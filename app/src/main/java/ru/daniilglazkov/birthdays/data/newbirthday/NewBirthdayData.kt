package ru.daniilglazkov.birthdays.data.newbirthday

import ru.daniilglazkov.birthdays.domain.birthdays.newbirthday.NewBirthdayDomain
import java.time.LocalDate

/**
 * @author Danil Glazkov on 06.09.2022, 01:12
 */
interface NewBirthdayData {
    fun <T> map(mapper: Mapper<T>): T

    class Base(private val name: String, private val date: LocalDate) : NewBirthdayData {
        override fun <T> map(mapper: Mapper<T>): T = mapper.map(name, date)
    }
    class Empty : NewBirthdayData {
        override fun <T> map(mapper: Mapper<T>): T = mapper.map("", LocalDate.now())
    }

    interface Mapper<T> {
        fun map(name: String, date: LocalDate): T

        abstract class Abstract<T>(private val data: (String, LocalDate) -> T) : Mapper<T> {
            override fun map(name: String, date: LocalDate): T = data.invoke(name, date)
        }
        class ToDomain : Abstract<NewBirthdayDomain>(NewBirthdayDomain::Base)
        class ToDatabaseModel : Abstract<NewBirthdayEntity>(::NewBirthdayEntity)
    }

}