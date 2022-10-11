package ru.daniilglazkov.birthdays.ui.core

/**
 * @author Danil Glazkov on 11.06.2022, 03:00
 */
interface Mapper<S, R> {
    fun map(source: S): R

    interface Unit<T> : Mapper<T, kotlin.Unit>
}