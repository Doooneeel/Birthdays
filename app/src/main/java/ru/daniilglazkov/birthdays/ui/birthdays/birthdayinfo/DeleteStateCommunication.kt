package ru.daniilglazkov.birthdays.ui.birthdays.birthdayinfo

import ru.daniilglazkov.birthdays.ui.core.Communication

/**
 * @author Danil Glazkov on 06.10.2022, 14:18
 */
interface DeleteStateCommunication : Communication.Mutable<Boolean> {
    fun handleTrue(block: () -> Unit)

    class Base : Communication.UiUpdate<Boolean>(initValue = false) , DeleteStateCommunication {
        override fun handleTrue(block: () -> Unit) {
            if (value) block.invoke()
        }
    }
}