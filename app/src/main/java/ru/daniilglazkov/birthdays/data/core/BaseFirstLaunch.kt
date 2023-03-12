package ru.daniilglazkov.birthdays.data.core

import ru.daniilglazkov.birthdays.data.core.cache.PreferencesDataStore
import ru.daniilglazkov.birthdays.domain.core.FirstLaunch

/**
 * @author Danil Glazkov on 26.02.2023, 7:24
 */
class BaseFirstLaunch(
    private val preferencesDataStore: PreferencesDataStore.Mutable<Boolean>,
    private val key: String
) : FirstLaunch {
    private var firstLaunch = preferencesDataStore.read(key, true)

    override fun firstLaunch(): Boolean = if (firstLaunch) {
        preferencesDataStore.save(key, false)
        firstLaunch = false
        true
    } else {
        false
    }
}