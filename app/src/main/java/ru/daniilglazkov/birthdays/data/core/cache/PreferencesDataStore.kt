package ru.daniilglazkov.birthdays.data.core.cache

import android.content.SharedPreferences
import android.content.SharedPreferences.Editor

/**
 * @author Danil Glazkov on 26.02.2023, 7:31
 */
interface PreferencesDataStore {

    interface Read<T> {
        fun read(key: String, defaultValue: T): T
    }

    interface Save<T> {
        fun save(key: String, value: T)
    }

    interface Mutable<T> : Read<T>, Save<T>


    abstract class Abstract<T>(private val preferences: SharedPreferences) : Mutable<T> {
        protected abstract fun Editor.put(key: String, value: T): Editor

        override fun save(key: String, value: T) = preferences.edit()
            .put(key, value)
            .apply()
    }


    class Boolean(
        private val preferences: SharedPreferences,
    ) : Abstract<kotlin.Boolean>(preferences) {
        override fun Editor.put(key: String, value: kotlin.Boolean): Editor = putBoolean(key, value)

        override fun read(key: String, defaultValue: kotlin.Boolean): kotlin.Boolean =
            preferences.getBoolean(key, defaultValue)
    }
}