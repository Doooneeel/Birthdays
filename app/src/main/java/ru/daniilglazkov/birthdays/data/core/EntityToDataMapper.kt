package ru.daniilglazkov.birthdays.data.core

/**
 * @author Danil Glazkov on 12.09.2022, 03:04
 */
interface EntityToDataMapper<T> {
    fun mapToData(): T
}