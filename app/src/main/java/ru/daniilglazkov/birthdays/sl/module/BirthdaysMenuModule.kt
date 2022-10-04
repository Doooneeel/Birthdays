package ru.daniilglazkov.birthdays.sl.module

import ru.daniilglazkov.birthdays.domain.birthdays.showmode.BirthdayListShowModeInteractor
import ru.daniilglazkov.birthdays.sl.core.Module
import ru.daniilglazkov.birthdays.ui.birthdays.sheetmenu.MenuSheetViewModel
import ru.daniilglazkov.birthdays.ui.birthdays.showmode.ShowModeCommunication
import ru.daniilglazkov.birthdays.ui.birthdays.showmode.ShowModeDomainToUiMapper

/**
 * @author Danil Glazkov on 21.07.2022, 22:58
 */
class BirthdaysMenuModule(
    private val interactor: BirthdayListShowModeInteractor,
) : Module<MenuSheetViewModel.Base> {
    override fun viewModel(): MenuSheetViewModel.Base {
        val communication = ShowModeCommunication.Base()
        val showModeDomainToUiMapper = ShowModeDomainToUiMapper.Base()

        return MenuSheetViewModel.Base(interactor, communication, showModeDomainToUiMapper)
    }
}