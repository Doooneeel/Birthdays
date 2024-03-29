package ru.daniilglazkov.birthdays.ui.birthdaylist.recycler

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import ru.daniilglazkov.birthdays.databinding.BirthdayItemLayoutBinding
import ru.daniilglazkov.birthdays.databinding.BirthdayItemMessageBinding
import ru.daniilglazkov.birthdays.databinding.BirthdayTodayItemLayoutBinding
import ru.daniilglazkov.birthdays.databinding.HeaderLayoutBinding
import ru.daniilglazkov.birthdays.ui.birthdaylist.BirthdayItemUi
import ru.daniilglazkov.birthdays.ui.core.view.click.OnClickCallback
import ru.daniilglazkov.birthdays.ui.core.view.recycler.DiffUtilCallback
import ru.daniilglazkov.birthdays.ui.main.BaseAdapter

/**
 * @author Danil Glazkov on 10.06.2022, 02:14
 */
class BirthdayListAdapter(
    private val itemOnClickCallback: OnClickCallback<BirthdayItemUi>,
) : BaseAdapter<BirthdayListViewHolder, BirthdayItemUi>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BirthdayListViewHolder {
        return when(viewType) {
            BIRTHDAY -> BirthdayListViewHolder.Base(
                BirthdayItemLayoutBinding.inflate(layoutInflater, parent, false),
                itemOnClickCallback
            )
            HEADER -> BirthdayListViewHolder.Header(
                HeaderLayoutBinding.inflate(layoutInflater, parent, false)
            )
            MESSAGE -> BirthdayListViewHolder.Message(
                BirthdayItemMessageBinding.inflate(layoutInflater, parent, false)
            )
            BIRTHDAY_TODAY -> BirthdayListViewHolder.Today(
                BirthdayTodayItemLayoutBinding.inflate(layoutInflater, parent, false),
                itemOnClickCallback
            )
            else -> throw IllegalArgumentException("unknown viewType: [$viewType]")
        }
    }

    override fun diffUtilCallback(
        oldList: MutableList<BirthdayItemUi>,
        newList: List<BirthdayItemUi>
    ) : DiffUtil.Callback = DiffUtilCallback(oldList, newList)

    override fun getViewType(data: BirthdayItemUi) = when(data) {
        is BirthdayItemUi.Base -> BIRTHDAY
        is BirthdayItemUi.Header -> HEADER
        is BirthdayItemUi.Today -> BIRTHDAY_TODAY
        is BirthdayItemUi.Message -> MESSAGE
        else -> -1
    }

    companion object {
        private const val BIRTHDAY: Int = 0
        private const val HEADER: Int = 1
        private const val BIRTHDAY_TODAY: Int = 2
        private const val MESSAGE: Int = 3
    }
}