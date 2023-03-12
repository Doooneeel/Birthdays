package ru.daniilglazkov.birthdays.domain.settings

/**
 * @author Danil Glazkov on 08.10.2022, 13:00
 */
interface FetchSettings {
    suspend fun fetchSettings(): SettingsDomain
}