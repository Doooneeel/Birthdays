package ru.daniilglazkov.birthdays.ui.core.view.recycler

import androidx.recyclerview.widget.DiffUtil
import ru.daniilglazkov.birthdays.ui.core.Same

/**
 * @author Danil Glazkov on 12.06.2022, 18:45
 */
class DiffUtilCallback<T: Same<T>>(
    private val oldList: List<T>,
    private val newList: List<T>
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].same(newList[newItemPosition])

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].sameContent(newList[newItemPosition])
}