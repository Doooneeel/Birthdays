package ru.daniilglazkov.birthdays.domain.core

/**
 * @author Danil Glazkov on 03.03.2023, 11:30
 */
interface CompareId {
    fun compareId(id: Int): Boolean
}