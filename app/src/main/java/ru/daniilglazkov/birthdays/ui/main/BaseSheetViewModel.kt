package ru.daniilglazkov.birthdays.ui.main

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import ru.daniilglazkov.birthdays.ui.core.Communication
import ru.daniilglazkov.birthdays.ui.core.navigation.NavigateBack
import ru.daniilglazkov.birthdays.ui.core.navigation.Navigation
import ru.daniilglazkov.birthdays.ui.core.ObserveCloseDialog

/**
 * @author Danil Glazkov on 04.09.2022, 21:23
 */
interface BaseSheetViewModel<VT> : BaseViewModel<VT>, NavigateBack, ObserveCloseDialog {

    abstract class Abstract<VT : Any>(
        communication: Communication.Mutable<VT>,
        navigation: Navigation.Mutable = Navigation.Unit(),
    ) : BaseViewModel.Abstract<VT>(communication, navigation),
        NavigateBack,
        ObserveCloseDialog
    {
        private val closeDialogLiveData = MutableLiveData<Unit>()

        override fun navigateBack() {
            closeDialogLiveData.value = Unit
        }
        override fun observeCloseDialog(owner: LifecycleOwner, observer: Observer<Unit>) =
            closeDialogLiveData.observe(owner, observer)
    }
}