package ru.daniilglazkov.birthdays.ui.birthdaylist.chips

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import ru.daniilglazkov.birthdays.ui.core.Communication

/**
 * @author Danil Glazkov on 11.08.2022, 04:51
 */
interface BirthdayChipCommunication : Communication.Mutable<ChipListUi> {

    interface Put {
        fun putChips(chips: ChipListUi)
    }

    interface Observe {
        fun observeChips(owner: LifecycleOwner, observer: Observer<ChipListUi>)
    }

    class Base : Communication.Ui<ChipListUi>(), BirthdayChipCommunication
}