package ru.daniilglazkov.birthdays.sl.module.datetime

import java.time.LocalDate

/**
 * @author Danil Glazkov on 29.10.2022, 05:31
 */
interface ProvideCurrentDate {
    fun provideCurrentDate(): LocalDate
}