package ru.daniilglazkov.birthdays.core

/**
 * @author Danil Glazkov on 11.09.2022, 22:19
 */
interface HandleException {
    fun handle(exception: Exception): Exception

    class Empty : HandleException {
        override fun handle(exception: Exception): Exception = exception
    }
}