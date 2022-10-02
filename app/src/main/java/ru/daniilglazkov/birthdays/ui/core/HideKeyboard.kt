package ru.daniilglazkov.birthdays.ui.core

import android.os.IBinder

/**
 * @author Danil Glazkov on 02.10.2022, 11:05
 */
interface HideKeyboard {
    fun hideKeyboard(windowToken: IBinder)
}