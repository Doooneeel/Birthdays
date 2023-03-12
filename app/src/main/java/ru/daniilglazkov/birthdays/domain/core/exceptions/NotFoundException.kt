package ru.daniilglazkov.birthdays.domain.core.exceptions

/**
 * @author Danil Glazkov on 29.06.2022, 15:01
 */
data class NotFoundException(private val text: String = "") : Exception(text)