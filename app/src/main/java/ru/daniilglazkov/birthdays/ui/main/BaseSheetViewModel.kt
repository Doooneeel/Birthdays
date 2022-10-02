package ru.daniilglazkov.birthdays.ui.main

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import ru.daniilglazkov.birthdays.ui.core.Communication
import ru.daniilglazkov.birthdays.ui.core.Navigation

/**
 * @author Danil Glazkov on 04.09.2022, 21:23
 */
abstract class BaseSheetViewModel<VT : Any>(
    communication: Communication.Mutable<VT>,
    navigation: Navigation.Mutable = Navigation.Unit(),
) : BaseViewModel<VT>(communication, navigation) {
    private val closeDialogLiveData = MutableLiveData<Unit>()

    protected fun navigateBack() = closeDialogLiveData.setValue(Unit)

    fun observeCloseDialog(owner: LifecycleOwner, observer: Observer<Unit>) =
        closeDialogLiveData.observe(owner, observer)
}