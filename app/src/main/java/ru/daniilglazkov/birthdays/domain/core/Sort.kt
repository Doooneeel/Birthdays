package ru.daniilglazkov.birthdays.domain.core

/**
 * @author Danil Glazkov on 31.07.2022, 17:52
 */
interface Sort<T> {
    fun sort(data: T): T
}