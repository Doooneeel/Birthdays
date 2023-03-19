package ru.daniilglazkov.birthdays.sl.core

import ru.daniilglazkov.birthdays.service.birthday.notification.BirthdayNotificationMapper

interface ProvideNotificationMapper {
    fun provideNotificationMapper(): BirthdayNotificationMapper
}