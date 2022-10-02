package ru.daniilglazkov.birthdays.ui.birthdays.list

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import ru.daniilglazkov.birthdays.databinding.BirthdayItemLayoutBinding
import ru.daniilglazkov.birthdays.databinding.BirthdayItemMessageBinding
import ru.daniilglazkov.birthdays.databinding.BirthdayTodayItemLayoutBinding
import ru.daniilglazkov.birthdays.databinding.HeaderLayoutBinding
import ru.daniilglazkov.birthdays.ui.birthdays.BirthdayUi
import ru.daniilglazkov.birthdays.ui.core.DiffUtilCallback
import ru.daniilglazkov.birthdays.ui.core.click.OnSingleClickCallback
import ru.daniilglazkov.birthdays.ui.main.BaseAdapter

/**
 * @author Danil Glazkov on 10.06.2022, 02:14
 */
class BirthdaysAdapter(
    private val itemOnClickCallback: OnSingleClickCallback<BirthdayUi>,
    private val onEmptySpaceClick: () -> Unit
) : BaseAdapter<BirthdaysViewHolder, BirthdayUi>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BirthdaysViewHolder {
        return when(viewType) {
            BIRTHDAY -> BirthdaysViewHolder.Base(
                BirthdayItemLayoutBinding.inflate(layoutInflater, parent, false),
                itemOnClickCallback
            )
            HEADER -> BirthdaysViewHolder.Header(
                HeaderLayoutBinding.inflate(layoutInflater, parent, false),
                onEmptySpaceClick
            )
            MESSAGE -> BirthdaysViewHolder.Message(
                BirthdayItemMessageBinding.inflate(layoutInflater, parent, false),
                onEmptySpaceClick
            )
            BIRTHDAY_TODAY -> BirthdaysViewHolder.Today(
                BirthdayTodayItemLayoutBinding.inflate(layoutInflater, parent, false),
                itemOnClickCallback
            )
            else -> throw IllegalArgumentException("unknown viewType: [$viewType]")
        }
    }

    override fun diffUtilCallback(
        oldList: MutableList<BirthdayUi>,
        newList: List<BirthdayUi>
    ) : DiffUtil.Callback = DiffUtilCallback(oldList, newList)

    override fun getViewType(data: BirthdayUi) = when(data) {
        is BirthdayUi.Base -> BIRTHDAY
        is BirthdayUi.Header -> HEADER
        is BirthdayUi.Today -> BIRTHDAY_TODAY
        is BirthdayUi.Message -> MESSAGE
        else -> -1
    }

    companion object {
        private const val BIRTHDAY: Int = 0
        private const val HEADER: Int = 1
        private const val BIRTHDAY_TODAY: Int = 2
        private const val MESSAGE: Int = 3
    }
}