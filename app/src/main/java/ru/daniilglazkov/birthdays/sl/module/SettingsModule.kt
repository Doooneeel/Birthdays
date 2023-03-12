package ru.daniilglazkov.birthdays.sl.module

import ru.daniilglazkov.birthdays.domain.settings.*
import ru.daniilglazkov.birthdays.ui.core.SheetCommunication
import ru.daniilglazkov.birthdays.ui.settings.*
import ru.daniilglazkov.birthdays.sl.core.*

/**
 * @author Danil Glazkov on 21.07.2022, 22:58
 */
class SettingsModule(
    private val coreModule: CoreModule,
    private val repository: SettingsRepository,
) : Module<SettingsViewModel.Base> {

    override fun viewModel(): SettingsViewModel.Base {
        val communication = SettingsCommunication.Base()
        val dispatchers = coreModule.dispatchers()

        val interactor = SettingsInteractor.Base(
            repository,
            HandleSettingsDataRequest.Base(),
            ChangeSettings.Base()
        )

        return SettingsViewModel.Base(
            interactor,
            HandleSettingsChange.Base(
                dispatchers,
                SettingsResponseMapper.Base(communication)
            ),
            communication,
            dispatchers,
            SheetCommunication.Base()
        )
    }
}