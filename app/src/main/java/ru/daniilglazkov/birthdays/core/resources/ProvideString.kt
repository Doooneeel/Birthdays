package ru.daniilglazkov.birthdays.core.resources

import androidx.annotation.PluralsRes
import androidx.annotation.StringRes

/**
 * @author Danil Glazkov on 10.06.2022, 00:46
 */
interface ProvideString {
    fun string(@StringRes id: Int): String
    fun quantityString(@PluralsRes id: Int, value: Int): String
}