package ru.daniilglazkov.birthdays.domain.birthdays.showmode

/**
 * @author Danil Glazkov on 04.08.2022, 03:27
 */
interface ShowModeRepository {
    fun saveShowMode(showMode: ShowModeDomain)
    fun readShowMode(): ShowModeDomain
}
