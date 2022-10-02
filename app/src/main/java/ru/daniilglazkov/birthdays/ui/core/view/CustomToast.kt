package ru.daniilglazkov.birthdays.ui.core.view

import android.content.Context
import android.widget.Toast

/**
 * @author Danil Glazkov on 26.08.2022, 12:40
 */
class CustomToast(context: Context) : Toast(context), AbstractView.Text {
    override fun map(source: String) {
        setText(source)
        show()
    }
}