package ru.daniilglazkov.birthdays.domain.core.exceptions

/**
 * @author Danil Glazkov on 09.10.2022, 15:29
 */
data class EmptyCacheException(private val text: String = "") : Exception(text)