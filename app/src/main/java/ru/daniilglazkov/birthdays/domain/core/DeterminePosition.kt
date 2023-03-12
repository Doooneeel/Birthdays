package ru.daniilglazkov.birthdays.domain.core

/**
 * @author Danil Glazkov on 02.02.2022, 07:36
 */
interface DeterminePosition {
    fun position(id: Int): Int
}