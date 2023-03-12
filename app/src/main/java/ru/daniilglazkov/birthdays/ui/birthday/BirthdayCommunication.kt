package ru.daniilglazkov.birthdays.ui.birthday

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import ru.daniilglazkov.birthdays.ui.core.Communication

/**
 * @author Danil Glazkov on 17.06.2022, 08:01
 */
interface BirthdayCommunication : Communication.Mutable<BirthdayUi> {

    interface Put {
        fun putBirthday(birthday: BirthdayUi)
    }

    interface Observe {
        fun observeBirthday(owner: LifecycleOwner, observer: Observer<BirthdayUi>)
    }

    class Base : Communication.Ui<BirthdayUi>(), BirthdayCommunication
}