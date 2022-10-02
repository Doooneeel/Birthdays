package ru.daniilglazkov.birthdays.data.core

/**
 * @author Danil Glazkov on 05.09.2022, 20:11
 */
interface MutablePreferences<T> : PreferencesRead<T>, PreferencesSave<T>