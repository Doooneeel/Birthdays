package ru.daniilglazkov.birthdays.ui.core

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer

/**
 * @author Danil Glazkov on 10.10.2022, 18:21
 */
interface ObserveCloseDialog {
    fun observeCloseDialog(owner: LifecycleOwner, observer: Observer<Unit>)
}