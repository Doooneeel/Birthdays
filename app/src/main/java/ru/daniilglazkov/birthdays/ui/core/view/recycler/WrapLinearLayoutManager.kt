package ru.daniilglazkov.birthdays.ui.core.view.recycler

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * @author Danil Glazkov on 05.03.2023, 10:50
 */
class WrapLinearLayoutManager(
    context: Context,
    orientation: Int = RecyclerView.VERTICAL,
) : LinearLayoutManager(
    context,
    orientation,
    false
) {
    override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State) {
        runCatching { super.onLayoutChildren(recycler, state) }.onFailure { throwable ->
            throwable.printStackTrace()
        }
    }
}