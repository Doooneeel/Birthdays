package ru.daniilglazkov.birthdays.ui.core.view

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
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
) , AbstractView.Recycler {
    override fun scrollUp(needToScroll: Boolean) {
       if (needToScroll) {
           (layoutManager as LinearLayoutManager).scrollToPositionWithOffset(0, 0)
       }
    }
    override fun nestedScroll(enabled: Boolean) {
        isNestedScrollingEnabled = enabled
    }
}