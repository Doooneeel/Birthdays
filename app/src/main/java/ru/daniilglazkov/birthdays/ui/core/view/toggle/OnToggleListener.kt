package ru.daniilglazkov.birthdays.ui.core.view.toggle

/**
 * @author Danil Glazkov on 03.10.2022, 17:45
 */
fun interface OnToggleListener {

    fun onToggle(newState: Boolean)


    object Unit : OnToggleListener {
        override fun onToggle(newState: Boolean) = kotlin.Unit
    }
}