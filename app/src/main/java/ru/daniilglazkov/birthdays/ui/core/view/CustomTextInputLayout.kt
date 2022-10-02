package ru.daniilglazkov.birthdays.ui.core.view

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.textfield.TextInputLayout

/**
 * @author Danil Glazkov on 12.08.2022, 23:43
 */
class CustomTextInputLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : TextInputLayout(
    context,
    attrs
) , AbstractView.Text {
    override fun map(source: String) { error = source }
}