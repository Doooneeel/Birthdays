package ru.daniilglazkov.birthdays.ui.core.view

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView

/**
 * @author Danil Glazkov on 14.06.2022, 06:58
 */
class CustomRecyclerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : RecyclerView(
    context,
    attrs,
) , AbstractView.Scroll {
    override fun map(source: Boolean) {
        if (source) {
            smoothScrollToPosition(-10)
        }
    }
}