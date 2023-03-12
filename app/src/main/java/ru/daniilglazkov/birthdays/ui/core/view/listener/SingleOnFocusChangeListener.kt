package ru.daniilglazkov.birthdays.ui.core.view.listener

import android.view.View

/**
 * @author Danil Glazkov on 13.11.2022, 19:19
 */
interface SingleOnFocusChangeListener : View.OnFocusChangeListener {

    class OutOfFocus(private val outOfFocus: () -> Unit) : SingleOnFocusChangeListener {
        override fun onFocusChange(v: View?, hasFocus: Boolean) {
            if (!hasFocus) outOfFocus.invoke()
        }
    }
}