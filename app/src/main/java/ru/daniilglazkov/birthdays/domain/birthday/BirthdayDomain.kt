package ru.daniilglazkov.birthdays.domain.birthday

import ru.daniilglazkov.birthdays.domain.core.Matches
import java.time.LocalDate

/**
 * @author Danil Glazkov on 10.06.2022, 01:00
 */
interface BirthdayDomain : Matches<BirthdayDomain> {
    fun <T> map(mapper: Mapper<T>): T

    abstract class Abstract(
        private val id: Int,
        private val name: String,
        private val date: LocalDate,
        private val type: BirthdayType
    ) : BirthdayDomain {
        private val compare = BirthdayCheckMapper.Compare(id, name, date, type)

        override fun <T> map(mapper: Mapper<T>): T = mapper.map(id, name, date, type)
        override fun matches(data: BirthdayDomain): Boolean = data.map(compare)
    }

    class Base(id: Int, name: String, date: LocalDate) : Abstract(id, name, date, BirthdayType.Base)
    class Header(id: Int, name: String) : Abstract(id, name, LocalDate.MIN, BirthdayType.Header)

    interface Mapper<T> {
        fun map(id: Int, name: String, date: LocalDate, type: BirthdayType): T
    }
}