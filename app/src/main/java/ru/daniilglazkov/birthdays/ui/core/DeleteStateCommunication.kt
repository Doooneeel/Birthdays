package ru.daniilglazkov.birthdays.ui.core

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer

/**
 * @author Danil Glazkov on 06.10.2022, 14:18
 */
interface DeleteStateCommunication : Communication.Mutable<Boolean> {
    fun handleTrue(block: () -> Unit)

    class Base : Communication.Ui<Boolean>(initValue = false), DeleteStateCommunication {
        override fun handleTrue(block: () -> Unit) {
            if (value) block.invoke()
        }
    }

    interface Observe {
        fun observeDeleteState(owner: LifecycleOwner, observer: Observer<Boolean>)
    }
}