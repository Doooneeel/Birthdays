package ru.daniilglazkov.birthdays.ui.birthdays.sheetmenu

import ru.daniilglazkov.birthdays.domain.birthdays.showmode.BirthdayListShowModeInteractor
import ru.daniilglazkov.birthdays.domain.birthdays.showmode.ShowModeDomain
import ru.daniilglazkov.birthdays.domain.birthdays.showmode.sort.SortMode
import ru.daniilglazkov.birthdays.ui.birthdays.showmode.ShowModeCommunication
import ru.daniilglazkov.birthdays.ui.birthdays.showmode.ShowModeDomainToUiMapper
import ru.daniilglazkov.birthdays.ui.birthdays.showmode.ShowModeUi
import ru.daniilglazkov.birthdays.ui.main.BaseSheetViewModel

/**
 * @author Danil Glazkov on 21.07.2022, 22:33
 */
class MenuSheetViewModel(
    private val interactor: BirthdayListShowModeInteractor,
    private val communication: ShowModeCommunication,
    private val toUi: ShowModeDomainToUiMapper
) : BaseSheetViewModel<ShowModeUi>(communication) {
    private val change = { showModeDomain: ShowModeDomain ->
        communication.map(showModeDomain.map(toUi))
    }
    fun init(firstCall: Boolean) {
        if (firstCall) change(interactor.read())
    }

    fun changeSortMode(sort: SortMode) {
        change(interactor.changeSortMode(sort))
    }
    fun changeFlags(reverse: Boolean, group: Boolean) {
        change(interactor.changeFlags(reverse, group))
    }
}