package ru.daniilglazkov.birthdays.ui.newbirthday.about

import ru.daniilglazkov.birthdays.R
import ru.daniilglazkov.birthdays.domain.newbirthday.AboutBirthdateDomain
import ru.daniilglazkov.birthdays.ui.core.resources.ProvideString

/**
 * @author Danil Glazkov on 10.10.2022, 16:19
 */
interface AboutBirthdateDomainToUiMapper : AboutBirthdateDomain.Mapper<AboutBirthdateUi>{

    class Base(private val resources: ProvideString) : AboutBirthdateDomainToUiMapper {
        override fun map(turnsYearsOld: Int, daysToBirthday: Int) = AboutBirthdateUi.Base(
            resources.quantityString(R.plurals.age, turnsYearsOld),
            resources.quantityString(R.plurals.day, daysToBirthday)
        )
    }
}