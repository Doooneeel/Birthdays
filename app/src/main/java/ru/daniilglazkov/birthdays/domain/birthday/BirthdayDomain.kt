package ru.daniilglazkov.birthdays.domain.birthday

import java.time.LocalDate

/**
 * @author Danil Glazkov on 10.06.2022, 01:00
 */
interface BirthdayDomain {

    fun <T> map(mapper: Mapper<T>): T

    fun compareId(id: Int): Boolean

    interface Mapper<T> {
        fun map(id: Int, name: String, date: LocalDate, type: BirthdayType): T
    }


    abstract class Abstract(
        private val id: Int,
        private val name: String,
        private val date: LocalDate,
        private val type: BirthdayType
    ) : BirthdayDomain {
        override fun compareId(id: Int): Boolean = this.id == id

        override fun <T> map(mapper: Mapper<T>): T = mapper.map(id, name, date, type)
    }

    data class Base(
        private val id: Int,
        private val name: String,
        private val date: LocalDate,
    ) : Abstract(id, name, date, BirthdayType.Base)

    data class Header(
        private val id: Int,
        private val name: String,
    ) : Abstract(id, name, LocalDate.MIN, BirthdayType.Header)

    data class Mock(
        private val name: String = "",
        private val date: LocalDate = LocalDate.MIN
    ) : Abstract(Int.MIN_VALUE, name, date, BirthdayType.Mock)
}



















