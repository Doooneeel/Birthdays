package ru.daniilglazkov.birthdays.ui.core.view.recycler

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView
import ru.daniilglazkov.birthdays.ui.core.view.AbstractView

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

    private val recyclerViewScroll get() = RecyclerViewScroll.Base(
        layoutManager = layoutManager,
        linearSmoothScroller = BaseLinearSmoothScroller(context, scrollDuration = 22f),
    )

    override fun scroll(scrollMode: ScrollMode) {
        scrollMode.scroll(recyclerViewScroll)
    }

    override fun nestedScroll(enabled: Boolean) {
        isNestedScrollingEnabled = enabled
    }
}