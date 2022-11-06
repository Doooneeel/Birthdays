package ru.daniilglazkov.birthdays.ui.birthdaylist.recycler

import android.view.View
import androidx.viewbinding.ViewBinding
import ru.daniilglazkov.birthdays.databinding.*
import ru.daniilglazkov.birthdays.ui.birthdaylist.BirthdayItemUi
import ru.daniilglazkov.birthdays.ui.core.click.OnSingleClickCallback
import ru.daniilglazkov.birthdays.ui.core.view.AbstractView
import ru.daniilglazkov.birthdays.ui.core.view.CustomTextView
import ru.daniilglazkov.birthdays.ui.main.BaseViewHolder

/**
 * @author Danil Glazkov on 10.06.2022, 02:14
 */
abstract class BirthdayListViewHolder(itemView: View) : BaseViewHolder<BirthdayItemUi>(itemView) {

    abstract class AbstractHeader<VB : ViewBinding>(
        private val binding: VB,
        private val onClick: () -> Unit,
        private val textView: CustomTextView
    ) : BirthdayListViewHolder(binding.root) {
        override fun bind(data: BirthdayItemUi) {
            binding.root.setOnClickListener {
                onClick.invoke()
            }
            data.applyHeader(textView)
        }
    }

    class Header(
        binding: HeaderLayoutBinding,
        onClick: () -> Unit
    ) : AbstractHeader<HeaderLayoutBinding>(
        binding,
        onClick,
        binding.headerTextView
    )

    class Message(
        binding: BirthdayItemMessageBinding,
        onClick: () -> Unit
    ) : AbstractHeader<BirthdayItemMessageBinding>(
        binding,
        onClick,
        binding.messageTextView
    )

    class Base(
        private val binding: BirthdayItemLayoutBinding,
        private val onClickCallback: OnSingleClickCallback<BirthdayItemUi>,
    ) : BirthdayListViewHolder(binding.root) {
        override fun bind(data: BirthdayItemUi) {
            binding.root.setOnClickListener {
                onClickCallback.onSingleClick(data)
            }
            data.apply(binding.nameTextView, binding.turnAgeTextView, binding.untilDayTextView)
        }
    }

    class Today(
        private val binding: BirthdayTodayItemLayoutBinding,
        private val onClickCallback: OnSingleClickCallback<BirthdayItemUi>,
    ) : BirthdayListViewHolder(binding.root) {
        override fun bind(data: BirthdayItemUi) {
            binding.root.setOnClickListener {
                onClickCallback.onSingleClick(data)
            }
            data.apply(binding.nameTextView, binding.turnAgeTextView, AbstractView.Text.Empty)
        }
    }
}