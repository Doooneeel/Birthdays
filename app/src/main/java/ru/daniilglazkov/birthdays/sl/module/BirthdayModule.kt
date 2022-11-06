package ru.daniilglazkov.birthdays.sl.module

import ru.daniilglazkov.birthdays.domain.birthday.BirthdayInteractor
import ru.daniilglazkov.birthdays.domain.birthdaylist.BirthdayListRepository
import ru.daniilglazkov.birthdays.domain.zodiac.*
import ru.daniilglazkov.birthdays.sl.core.CoreModule
import ru.daniilglazkov.birthdays.sl.core.Module
import ru.daniilglazkov.birthdays.ui.birthday.*
import ru.daniilglazkov.birthdays.ui.core.DeleteStateCommunication
import ru.daniilglazkov.birthdays.ui.core.ErrorCommunication
import ru.daniilglazkov.birthdays.ui.zodiac.*

/**
 * @author Danil Glazkov on 12.06.2022, 20:45
 */
class BirthdayModule(
    private val coreModule: CoreModule,
    private val dateModule: DateModule,
    private val zodiacGroupClassification: ZodiacGroupClassification,
    private val repository: BirthdayListRepository,
) : Module<BirthdayViewModel.Base> {
    override fun viewModel(): BirthdayViewModel.Base {
        val resources = coreModule.resourcesManager()

        val interactor = BirthdayInteractor.Base(repository,
            BirthdayDomainToZodiacMapper.Base(zodiacGroupClassification),
            BirthdayDomainToChineseZodiacMapper.Base(BaseChineseZodiacDomainList(resources))
        )
        val sheetBirthdayDomainToUiMapper = BirthdayDomainToUiMapper.Base(
            dateModule.provideNextEvent(),
            resources,
            dateModule.provideCurrentDate()
        )
        val birthdayZodiacsDomainToUiMapper = BirthdayZodiacsDomainToUiMapper.Base(
            ZodiacDomainToUiMapper.Base(),
            ChineseZodiacDomainToUiMapper.Base()
        )
        return BirthdayViewModel.Base(interactor,
            BirthdayCommunication.Base(),
            ErrorCommunication.Base(resources),
            BirthdayZodiacsCommunication.Base(),
            DeleteStateCommunication.Base(),
            sheetBirthdayDomainToUiMapper,
            birthdayZodiacsDomainToUiMapper,
        )
    }
}