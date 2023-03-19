package ru.daniilglazkov.birthdays.sl.module.datetime

import ru.daniilglazkov.birthdays.domain.datetime.CalculateNextEvent

/**
 * @author Danil Glazkov on 30.10.2022, 11:01
 */
interface ProvideNextEvent {
    fun provideNextEvent(): CalculateNextEvent
}