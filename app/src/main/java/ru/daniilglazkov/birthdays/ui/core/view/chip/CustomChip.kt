package ru.daniilglazkov.birthdays.ui.core.view.chip

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.chip.Chip
import ru.daniilglazkov.birthdays.ui.core.view.AbstractView

/**
 * @author Danil Glazkov on 05.10.2022, 13:12
 */
class CustomChip @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : Chip(context, attrs),
    AbstractView.Text
{
    override fun map(source: String) { text = source }
}
