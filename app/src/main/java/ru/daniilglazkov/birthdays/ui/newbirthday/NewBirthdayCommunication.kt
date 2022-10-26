package ru.daniilglazkov.birthdays.ui.newbirthday

import ru.daniilglazkov.birthdays.ui.core.Clear
import ru.daniilglazkov.birthdays.ui.core.Communication
import ru.daniilglazkov.birthdays.ui.core.ErrorMessage
import ru.daniilglazkov.birthdays.ui.core.textfilter.TextFilter
import ru.daniilglazkov.birthdays.ui.core.validate.Validate
import java.time.LocalDate

/**
 * @author Danil Glazkov on 25.08.2022, 13:40
 */
interface NewBirthdayCommunication : Communication.Mutable<NewBirthdayUi>, Clear {

    fun validate(successful: (NewBirthdayUi) -> Unit, onFailure: (ErrorMessage) -> Unit)
    fun filter(name: String, date: LocalDate, result: (NewBirthdayUi) -> Unit)


    class Base(
        private val validate: Validate,
        private val nameFilter: TextFilter,
    ) : Communication.Ui<NewBirthdayUi>(),
        NewBirthdayCommunication
    {
        override fun clear() = map(NewBirthdayUi.Empty)

        override fun validate(
            successful: (NewBirthdayUi) -> Unit,
            onFailure: (ErrorMessage) -> Unit
        ) = value.validate(
            validate,
            successful,
            onFailure
        )
        override fun filter(name: String, date: LocalDate, result: (NewBirthdayUi) -> Unit) {
            val filteredNewBirthday = NewBirthdayUi.Base(nameFilter.filter(name), date)
            filteredNewBirthday.also { newBirthday: NewBirthdayUi ->
                result.invoke(newBirthday)
                map(newBirthday)
            }
        }
    }

    interface Clear {
        fun clearNewBirthday()
    }

}