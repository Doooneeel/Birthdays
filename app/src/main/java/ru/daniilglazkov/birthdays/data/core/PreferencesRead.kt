package ru.daniilglazkov.birthdays.data.core

/**
 * @author Danil Glazkov on 05.09.2022, 20:10
 */
interface PreferencesRead<T> {
    fun read(key: String): T
}