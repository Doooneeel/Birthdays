package ru.daniilglazkov.birthdays.sl.module

import ru.daniilglazkov.birthdays.R
import ru.daniilglazkov.birthdays.ui.core.resources.ResourceManager
import ru.daniilglazkov.birthdays.data.newbirthday.*
import ru.daniilglazkov.birthdays.data.newbirthday.cache.*
import ru.daniilglazkov.birthdays.domain.birthdays.BirthdayListRepository
import ru.daniilglazkov.birthdays.domain.birthdays.newbirthday.NewBirthdayHandleException
import ru.daniilglazkov.birthdays.domain.birthdays.newbirthday.NewBirthdayInteractor
import ru.daniilglazkov.birthdays.domain.date.DateDifference
import ru.daniilglazkov.birthdays.domain.date.NextEvent
import ru.daniilglazkov.birthdays.sl.core.Module
import ru.daniilglazkov.birthdays.ui.birthdays.newbirthday.about.AboutBirthdateCommunication
import ru.daniilglazkov.birthdays.ui.birthdays.newbirthday.*
import ru.daniilglazkov.birthdays.ui.core.ErrorCommunication
import ru.daniilglazkov.birthdays.ui.core.textfilter.*
import ru.daniilglazkov.birthdays.ui.core.validate.*
import java.time.LocalDate

/**
 * @author Danil Glazkov on 19.06.2022, 15:01
 */
class NewBirthdayModule(
    private val resourceManager: ResourceManager,
    private val newBirthdayDao: NewBirthdayDao,
    private val repository: BirthdayListRepository,
    private val nextEvent: NextEvent,
    private val now: LocalDate
) : Module<NewBirthdayViewModel.Base> {
    override fun viewModel(): NewBirthdayViewModel.Base {
        val validate = ValidateChain(
            ValidateNotEmpty(
                resourceManager.string(R.string.empty_name_error_message)
            ),
            ValidateChain(
                ValidateFirstCharIsLetter(
                    resourceManager.string(R.string.first_char_is_not_letter_error_message)
                ),
                ValidateMinLength(
                    resourceManager.number(R.integer.name_min_length),
                    resourceManager.string(R.string.name_min_length_error_message)
                )
            )
        )
        val interactor = NewBirthdayInteractor.Base(
            repository,
            BaseNewBirthdayRepository(
                NewBirthdayCacheDataSource.Base(
                    newBirthdayDao,
                    NewBirthdayDataToCacheMapper.Base(),
                ),
                NewBirthdayDataToDomainMapper.Base(),
                NewBirthdayDomainToDataMapper.Base()
            ),
            DateDifference.NextEventInDays(nextEvent),
            DateDifference.YearsPlusOne(),
            NewBirthdayHandleException.Base(now),
            now
        )
        val nameFilter = TextFilterChain(TextFilterTrim(), TextFilterWhitespaces())

        return NewBirthdayViewModel.Base(
            interactor,
            NewBirthdayCommunication.Base(validate, nameFilter),
            ErrorCommunication.Base(resourceManager),
            AboutBirthdateCommunication.Base(resourceManager),
            NewBirthdayDomainToUiMapper(),
            NewBirthdayUi.Mapper.ToDomain()
        )
    }
}