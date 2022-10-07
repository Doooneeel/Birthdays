package ru.daniilglazkov.birthdays.ui.core.view.toggle

import android.content.res.ColorStateList
import android.widget.Button

/**
 * @author Danil Glazkov on 07.10.2022, 14:15
 */
interface ToggleButtonState {
    fun apply(view: Button)

    class Base(
        private val text: String,
        private val backgroundTint: ColorStateList,
        private val drawableId: Int,
    ) : ToggleButtonState {
        override fun apply(view: Button) {
            view.text = text
            view.backgroundTintList = backgroundTint
            view.setCompoundDrawablesWithIntrinsicBounds(drawableId, 0, 0, 0)
        }
    }
}