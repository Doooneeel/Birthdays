package ru.daniilglazkov.birthdays.sl.module.cache

import ru.daniilglazkov.birthdays.domain.birthdaylist.BirthdayListRepository

/**
 * @author Danil Glazkov on 17.03.2023, 20:07
 */
interface ProvideBirthdayListRepository {
    fun provideBirthdaysRepository(): BirthdayListRepository
}