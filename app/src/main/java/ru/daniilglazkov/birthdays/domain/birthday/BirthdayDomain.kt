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


    class Base(
        private val id: Int,
        private val name: String,
        private val date: LocalDate,
    ) : BirthdayDomain {
        private val now = LocalDate.now()

        private val type: BirthdayType =
            if (date.withYear(now.year) == now) BirthdayType.Today
            else BirthdayType.Base

        private val compare = BirthdayCheckMapper.Compare(id, name, date, type)

        override fun <T> map(mapper: Mapper<T>): T = mapper.map(id, name, date, type)
        override fun matches(data: BirthdayDomain): Boolean = data.map(compare)
    }

    class Header(name: String) : Abstract(-1, name, LocalDate.MIN, BirthdayType.Header)

    interface Mapper<T> {
        fun map(id: Int, name: String, date: LocalDate, type: BirthdayType): T
    }
}