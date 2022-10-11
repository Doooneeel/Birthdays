package ru.daniilglazkov.birthdays.ui.core.resources

import androidx.annotation.IntegerRes

/**
 * @author Danil Glazkov on 25.07.2022, 04:33
 */
interface ProvideNumber {
    fun number(@IntegerRes id: Int): Int
}