package ru.daniilglazkov.birthdays.ui.newbirthday

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import ru.daniilglazkov.birthdays.ui.core.*

/**
 * @author Danil Glazkov on 25.08.2022, 13:40
 */
interface NewBirthdayCommunication : Communication.Mutable<NewBirthdayUi> {

    fun <T> map(mapper: NewBirthdayUi.Mapper<T>): T


    interface Put {
        fun putNewBirthday(birthday: NewBirthdayUi)
    }

    interface Clear {
        fun clearNewBirthday()
    }

    interface Combo : Clear, Put


    interface Observe {
        fun observeNewBirthday(owner: LifecycleOwner, observer: Observer<NewBirthdayUi>)
    }


    class Base : Communication.Ui<NewBirthdayUi>(), NewBirthdayCommunication {
        override fun <T> map(mapper: NewBirthdayUi.Mapper<T>): T = value.map(mapper)
    }
}