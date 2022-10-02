package ru.daniilglazkov.birthdays.data.core

import android.content.SharedPreferences

/**
 * @author Danil Glazkov on 19.07.2022, 07:58
 */
interface PreferencesDataStore<T> : MutablePreferences<T> {

    class StringPreferences(
        private val preferences: SharedPreferences,
    ) : PreferencesDataStore<String> {
        override fun read(key: String): String =
            preferences.getString(key, null) ?: throw NullPointerException()

        override fun save(key: String, data: String) = preferences.edit()
            .putString(key, data)
            .apply()
    }
}
