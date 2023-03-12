package ru.daniilglazkov.birthdays.sl.core

import ru.daniilglazkov.birthdays.domain.date.EventIsToday

interface ProvideEventIsToday {
    fun provideEventIsToday(): EventIsToday
}