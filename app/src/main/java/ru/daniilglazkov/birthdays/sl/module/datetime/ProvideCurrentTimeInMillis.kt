package ru.daniilglazkov.birthdays.sl.module.datetime

import ru.daniilglazkov.birthdays.domain.datetime.CurrentTimeInMillis

/**
 * @author Danil Glazkov on 19.03.2023, 4:25
 */
interface ProvideCurrentTimeInMillis {
    fun provideCurrentTimeInMillis(): CurrentTimeInMillis
}