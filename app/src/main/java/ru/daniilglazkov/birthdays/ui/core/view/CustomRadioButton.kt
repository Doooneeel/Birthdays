package ru.daniilglazkov.birthdays.ui.core.view

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatRadioButton

/**
 * @author Danil Glazkov on 16.08.2022, 03:09
 */
class CustomRadioButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : AppCompatRadioButton(
    context,
    attrs
) , AbstractView.Check {
    override fun map(source: Boolean) { isChecked = source }
}