package ru.daniilglazkov.birthdays.ui.core

/**
 * @author Danil Glazkov on 24.08.2022, 13:13
 */
interface OnClosed<T> {
    fun onClosed(block: () -> T)

    interface Unit : OnClosed<kotlin.Unit>
}