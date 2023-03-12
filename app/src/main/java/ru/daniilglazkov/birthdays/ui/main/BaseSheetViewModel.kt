package ru.daniilglazkov.birthdays.ui.main

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import ru.daniilglazkov.birthdays.ui.core.ObserveCloseDialog
import ru.daniilglazkov.birthdays.ui.core.SheetCommunication
import ru.daniilglazkov.birthdays.ui.core.navigation.NavigateBack
import ru.daniilglazkov.birthdays.ui.core.navigation.Navigation

/**
 * @author Danil Glazkov on 04.09.2022, 21:23
 */
interface BaseSheetViewModel : BaseViewModel, NavigateBack, ObserveCloseDialog {

    abstract class Abstract(
        private val sheetCommunication: SheetCommunication,
        navigation: Navigation.Mutable = Navigation.Unit(),
    ) : BaseViewModel.Abstract(navigation), NavigateBack, ObserveCloseDialog {

        override fun navigateBack() = sheetCommunication.put(Unit)

        override fun observeCloseDialog(owner: LifecycleOwner, observer: Observer<Unit>) =
            sheetCommunication.observe(owner, observer)
    }
}