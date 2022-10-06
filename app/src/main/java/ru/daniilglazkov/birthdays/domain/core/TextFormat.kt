package ru.daniilglazkov.birthdays.domain.core

/**
 * @author Danil Glazkov on 06.10.2022, 00:13
 */
interface TextFormat<T> {
    fun format(source: T): String
}