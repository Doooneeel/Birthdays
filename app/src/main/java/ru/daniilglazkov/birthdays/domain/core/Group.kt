package ru.daniilglazkov.birthdays.domain.core

/**
 * @author Danil Glazkov on 01.08.2022, 21:47
 */
interface Group<T> {
    fun group(data: T): T

    abstract class Unit<T> : Group<T> {
        override fun group(data: T): T = data
    }
}
