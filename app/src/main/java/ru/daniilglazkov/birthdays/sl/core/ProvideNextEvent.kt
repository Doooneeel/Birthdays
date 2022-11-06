package ru.daniilglazkov.birthdays.sl.core

import ru.daniilglazkov.birthdays.domain.date.NextEvent

/**
 * @author Danil Glazkov on 30.10.2022, 11:01
 */
interface ProvideNextEvent {
    fun provideNextEvent(): NextEvent
}