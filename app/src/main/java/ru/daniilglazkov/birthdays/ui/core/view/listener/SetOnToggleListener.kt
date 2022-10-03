package ru.daniilglazkov.birthdays.ui.core.view.listener

/**
 * @author Danil Glazkov on 03.10.2022, 17:46
 */
interface SetOnToggleListener {
    fun setOnToggleListener(onToggleListener: OnToggleListener)
}

fun interface OnToggleListener {
    fun onToggle(newState: Boolean)
}