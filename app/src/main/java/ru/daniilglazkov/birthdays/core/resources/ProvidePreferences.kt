package ru.daniilglazkov.birthdays.core.resources

import android.content.SharedPreferences

/**
 * @author Danil Glazkov on 01.07.2022, 22:06
 */
interface ProvidePreferences {
    fun preferences(fileName: String): SharedPreferences
}