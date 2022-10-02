package ru.daniilglazkov.birthdays.ui.birthdays.list

import android.view.View
import androidx.viewbinding.ViewBinding
import ru.daniilglazkov.birthdays.databinding.BirthdayItemLayoutBinding
import ru.daniilglazkov.birthdays.databinding.BirthdayItemMessageBinding
import ru.daniilglazkov.birthdays.databinding.BirthdayTodayItemLayoutBinding
import ru.daniilglazkov.birthdays.databinding.HeaderLayoutBinding
import ru.daniilglazkov.birthdays.ui.birthdays.BirthdayUi
import ru.daniilglazkov.birthdays.ui.core.click.OnSingleClickCallback
import ru.daniilglazkov.birthdays.ui.core.view.CustomTextView
import ru.daniilglazkov.birthdays.ui.main.BaseViewHolder

/**
 * @author Danil Glazkov on 10.06.2022, 02:14
 */
abstract class BirthdaysViewHolder(itemView: View) : BaseViewHolder<BirthdayUi>(itemView) {

    abstract class Abstract<VB : ViewBinding>(
        private val binding: VB,
        private val onClick: () -> Unit,
        private val textView: CustomTextView
    ) : BirthdaysViewHolder(binding.root) {
        override fun bind(data: BirthdayUi) {
            binding.root.setOnClickListener {
                onClick.invoke()
            }
            data.apply(textView)
        }
    }

    class Header(
        binding: HeaderLayoutBinding,
        onClick: () -> Unit
    ) : Abstract<HeaderLayoutBinding>(
        binding,
        onClick,
        binding.headerTextView
    )

    class Message(
        binding: BirthdayItemMessageBinding,
        onClick: () -> Unit
    ) : Abstract<BirthdayItemMessageBinding>(
        binding,
        onClick,
        binding.messageTextView
    )

    class Base(
        private val binding: BirthdayItemLayoutBinding,
        private val onClickCallback: OnSingleClickCallback<BirthdayUi>,
    ) : BirthdaysViewHolder(binding.root) {
        override fun bind(data: BirthdayUi) {
            binding.root.setOnClickListener {
                onClickCallback.onSingleClick(data)
            }
            data.apply(
                nameView = binding.nameTextView,
                turnsView = binding.turnAgeTextView,
                untilView = binding.untilDayTextView
            )
        }
    }

    class Today(
        private val binding: BirthdayTodayItemLayoutBinding,
        private val onClickCallback: OnSingleClickCallback<BirthdayUi>,
    ) : BirthdaysViewHolder(binding.root) {
        override fun bind(data: BirthdayUi) {
            binding.root.setOnClickListener {
                onClickCallback.onSingleClick(data)
            }
            data.apply(
                nameView = binding.nameTextView,
                turnsView = binding.turnAgeTextView
            )
        }
    }
}