package ru.daniilglazkov.birthdays.ui.birthdaylist.chips

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import ru.daniilglazkov.birthdays.ui.core.Clear
import ru.daniilglazkov.birthdays.ui.core.Communication

/**
 * @author Danil Glazkov on 11.08.2022, 04:51
 */
interface BirthdayChipCommunication : Communication.Mutable<BirthdayListChips>, Clear {

    class Base : Communication.Ui<BirthdayListChips>(), BirthdayChipCommunication {
        override fun clear() = map(BirthdayListChips.Empty)
    }

    interface Observe {
        fun observeChips(owner: LifecycleOwner, observer: Observer<BirthdayListChips>)
    }
}