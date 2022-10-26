package ru.daniilglazkov.birthdays.ui.core.view

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

/**
 * @author Danil Glazkov on 24.10.2022, 21:40
 */
class CustomTextWithIconView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : AppCompatTextView(
    context,
    attrs
) , AbstractViewTextWithIcon {
    override fun map(textId: Int, iconId: Int) {
        setCompoundDrawablesWithIntrinsicBounds(iconId, 0, 0, 0)
        text = context.getText(textId)
    }
}