package ru.daniilglazkov.birthdays.ui.core.view.input

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.textfield.TextInputEditText
import ru.daniilglazkov.birthdays.ui.core.view.AbstractView

/**
 * @author Danil Glazkov on 23.08.2022, 16:56
 */
class CustomInputEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : TextInputEditText(
    context,
    attrs
) , AbstractView.Text {
    val text get() = getText()?.toString() ?: ""

    override fun map(source: String) = setText(source)
}