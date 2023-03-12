package ru.daniilglazkov.birthdays.ui.core.view.text

import android.content.Context
import android.widget.Toast
import ru.daniilglazkov.birthdays.ui.core.view.AbstractView

/**
 * @author Danil Glazkov on 26.08.2022, 12:40
 */
class CustomToast(context: Context) : Toast(context), AbstractView.Text {
    override fun map(source: String) {
        setText(source)
        show()
    }
}