package ru.daniilglazkov.birthdays.sl.module

import ru.daniilglazkov.birthdays.domain.showmode.ShowModeInteractor
import ru.daniilglazkov.birthdays.sl.core.Module
import ru.daniilglazkov.birthdays.ui.settings.SettingsViewModel
import ru.daniilglazkov.birthdays.ui.settings.showmode.ShowModeCommunication
import ru.daniilglazkov.birthdays.ui.settings.showmode.ShowModeDomainToUiMapper

/**
 * @author Danil Glazkov on 21.07.2022, 22:58
 */
class BirthdaysSettingsModule(
    private val interactor: ShowModeInteractor,
) : Module<SettingsViewModel.Base> {
    override fun viewModel() = SettingsViewModel.Base(
        interactor,
        ShowModeCommunication.Base(),
        ShowModeDomainToUiMapper.Base()
    )
}