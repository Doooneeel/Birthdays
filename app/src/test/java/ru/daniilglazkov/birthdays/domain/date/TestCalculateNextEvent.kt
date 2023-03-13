package ru.daniilglazkov.birthdays.domain.date

import java.time.LocalDate

/**
 * @author Danil Glazkov on 12.03.2023, 15:04
 */
class TestCalculateNextEvent(private val result: LocalDate) : CalculateNextEvent {
    val calledList = mutableListOf<LocalDate>()

    override fun nextEvent(date: LocalDate): LocalDate {
        calledList.add(date)
        return result
    }
}