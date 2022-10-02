package ru.daniilglazkov.birthdays.data.core

/**
 * @author Danil Glazkov on 02.08.2022, 20:46
 */
interface ToJson {
    fun <T> toJson(source: T) : String
}