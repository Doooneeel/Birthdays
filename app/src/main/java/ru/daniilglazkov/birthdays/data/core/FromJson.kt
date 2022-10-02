package ru.daniilglazkov.birthdays.data.core

/**
 * @author Danil Glazkov on 02.08.2022, 20:47
 */
interface FromJson  {
    fun <T> fromJson(json: String, clazz: Class<T>): T
}