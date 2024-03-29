package ru.daniilglazkov.birthdays.ui.core.view.text

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import ru.daniilglazkov.birthdays.ui.core.view.AbstractView

/**
 * @author Danil Glazkov on 17.06.2022, 08:48
 */
class CustomTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : AppCompatTextView(
    context,
    attrs
) , AbstractView.Text {
    override fun map(source: String) { text = source }
}