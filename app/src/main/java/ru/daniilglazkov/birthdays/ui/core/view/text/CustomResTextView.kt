package ru.daniilglazkov.birthdays.ui.core.view.text

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import ru.daniilglazkov.birthdays.ui.core.view.AbstractResView

/**
 * @author Danil Glazkov on 24.10.2022, 21:40
 */
class CustomResTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : AppCompatTextView(
    context,
    attrs
) , AbstractResView.Text {
    override fun map(source: Int) {
        text = context.getText(source)
    }
}