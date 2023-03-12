package ru.daniilglazkov.birthdays.data.settings.cache

/**
 * @author Danil Glazkov on 09.10.2022, 20:06
 */
interface ProvideSettingsDao {
    fun provideSettingsDao(): SettingsDao
}