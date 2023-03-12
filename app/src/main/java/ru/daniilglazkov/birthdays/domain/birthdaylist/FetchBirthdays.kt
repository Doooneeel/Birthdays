package ru.daniilglazkov.birthdays.domain.birthdaylist

/**
 * @author Danil Glazkov on 22.02.2023, 03:04
 */
interface FetchBirthdays {
    suspend fun birthdays(query: CharSequence = ""): BirthdayListResponse
}