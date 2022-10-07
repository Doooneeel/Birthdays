package ru.daniilglazkov.birthdays.sl.module

import ru.daniilglazkov.birthdays.domain.birthdays.showmode.BirthdayListShowModeInteractor
import ru.daniilglazkov.birthdays.sl.core.Module
import ru.daniilglazkov.birthdays.ui.birthdays.settings.SettingsViewModel
import ru.daniilglazkov.birthdays.ui.birthdays.settings.showmode.ShowModeCommunication
import ru.daniilglazkov.birthdays.ui.birthdays.settings.showmode.ShowModeDomainToUiMapper

/**
 * @author Danil Glazkov on 21.07.2022, 22:58
 */
class BirthdaysSettingsModule(
    private val interactor: BirthdayListShowModeInteractor,
) : Module<SettingsViewModel.Base> {
    override fun viewModel(): SettingsViewModel.Base {
        val communication = ShowModeCommunication.Base()
        val showModeDomainToUiMapper = ShowModeDomainToUiMapper.Base()

        return SettingsViewModel.Base(interactor, communication, showModeDomainToUiMapper)
    }
}