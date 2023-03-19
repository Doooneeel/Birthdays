package ru.daniilglazkov.birthdays.sl.module.datetime

import ru.daniilglazkov.birthdays.domain.datetime.EventIsToday

/**
 * @author Danil Glazkov on 19.03.2023, 16:01
 */
interface ProvideEventIsToday {
    fun provideEventIsToday(): EventIsToday
}