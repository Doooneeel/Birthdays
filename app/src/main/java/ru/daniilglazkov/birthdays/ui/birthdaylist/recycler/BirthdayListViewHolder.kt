package ru.daniilglazkov.birthdays.ui.birthdaylist.recycler

import android.view.View
import androidx.viewbinding.ViewBinding
import ru.daniilglazkov.birthdays.databinding.BirthdayItemLayoutBinding
import ru.daniilglazkov.birthdays.databinding.BirthdayItemMessageBinding
import ru.daniilglazkov.birthdays.databinding.BirthdayTodayItemLayoutBinding
import ru.daniilglazkov.birthdays.databinding.HeaderLayoutBinding
import ru.daniilglazkov.birthdays.ui.birthdaylist.BirthdayItemUi
import ru.daniilglazkov.birthdays.ui.core.view.AbstractView
import ru.daniilglazkov.birthdays.ui.core.view.click.OnClickCallback
import ru.daniilglazkov.birthdays.ui.main.BaseViewHolder

/**
 * @author Danil Glazkov on 10.06.2022, 02:14
 */
abstract class BirthdayListViewHolder(itemView: View) : BaseViewHolder<BirthdayItemUi>(itemView) {

    abstract class Abstract(
        private val root: View,
        private val onClickCallback: OnClickCallback<BirthdayItemUi>,
    ) : BirthdayListViewHolder(root) {

        protected abstract val name: AbstractView.Text
        protected abstract val turnsAge: AbstractView.Text
        protected abstract val daysToBirthday: AbstractView.Text

        override fun bind(data: BirthdayItemUi) {
            data.apply(name, turnsAge, daysToBirthday)

            root.setOnClickListener { root ->
                onClickCallback.onClick(data)
                root.rootView.requestFocus()
            }
        }
    }

    abstract class AbstractHeader<VB : ViewBinding>(
        private val binding: VB,
        private val view: AbstractView.Text
    ) : BirthdayListViewHolder(binding.root) {
        override fun bind(data: BirthdayItemUi) {
            data.applyHeader(view)

            binding.root.setOnClickListener { root ->
                root.requestFocus()
            }
        }
    }



    class Header(binding: HeaderLayoutBinding) :
        AbstractHeader<HeaderLayoutBinding>(binding, binding.headerTextView)

    class Message(binding: BirthdayItemMessageBinding) :
        AbstractHeader<BirthdayItemMessageBinding>(binding, binding.messageTextView)


    class Base(
        binding: BirthdayItemLayoutBinding,
        onClickCallback: OnClickCallback<BirthdayItemUi>,
    ) : Abstract(binding.root, onClickCallback) {
        override val name = binding.nameTextView
        override val turnsAge = binding.turnAgeTextView
        override val daysToBirthday = binding.untilDayTextView
    }

    class Today(
        binding: BirthdayTodayItemLayoutBinding,
        onClickCallback: OnClickCallback<BirthdayItemUi>,
    ) : Abstract(binding.root, onClickCallback) {
        override val name = binding.nameTextView
        override val turnsAge = binding.turnAgeTextView
        override val daysToBirthday = AbstractView.Text.Empty
    }
}