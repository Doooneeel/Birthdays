package ru.daniilglazkov.birthdays.domain.core

import java.time.LocalDate

/**
 * @author Danil Glazkov on 08.02.2023, 22:40
 */
interface ChangeDate {
    fun changeDate(date: LocalDate)
}