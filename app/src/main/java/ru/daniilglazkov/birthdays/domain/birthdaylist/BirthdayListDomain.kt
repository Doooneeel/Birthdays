package ru.daniilglazkov.birthdays.domain.birthdaylist

import ru.daniilglazkov.birthdays.domain.birthday.BirthdayDomain
import ru.daniilglazkov.birthdays.domain.core.*

/**
 * @author Danil Glazkov on 10.06.2022, 00:51
 */
interface BirthdayListDomain : DeterminePosition, AsList<BirthdayDomain> {

    fun <T> map(mapper: Mapper<T>): T

    interface Mapper<T> {
        fun map(list: List<BirthdayDomain>): T
    }


    abstract class Abstract(private val list: List<BirthdayDomain>) : BirthdayListDomain {

        override fun <T> map(mapper: Mapper<T>): T = mapper.map(list)

        override fun position(id: Int): Int = list.indexOfFirst { birthday ->
            birthday.compareId(id)
        }

        override fun asList(): List<BirthdayDomain> = list
    }

    data class Base(private val list: List<BirthdayDomain>) : Abstract(list) {
        constructor(vararg values: BirthdayDomain) : this(values.toList())
    }
}