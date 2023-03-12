package ru.daniilglazkov.birthdays.ui.birthdaylist

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import ru.daniilglazkov.birthdays.ui.core.Communication

/**
 * @author Danil Glazkov on 17.06.2022, 14:10
 */
interface BirthdayListCommunication : Communication.Mutable<BirthdayItemUiList> {

    interface Observe {
        fun observeBirthdayList(owner: LifecycleOwner, observer: Observer<BirthdayItemUiList>)
    }

    interface Put {
        fun putBirthdays(birthdays: BirthdayItemUiList)
    }

    class Base : Communication.Post<BirthdayItemUiList>(), BirthdayListCommunication
}