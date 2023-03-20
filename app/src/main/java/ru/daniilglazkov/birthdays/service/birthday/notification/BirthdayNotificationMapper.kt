package ru.daniilglazkov.birthdays.service.birthday.notification

import ru.daniilglazkov.birthdays.R
import ru.daniilglazkov.birthdays.domain.birthday.BirthdayDomain
import ru.daniilglazkov.birthdays.domain.birthday.BirthdayType
import ru.daniilglazkov.birthdays.domain.datetime.*
import ru.daniilglazkov.birthdays.ui.core.resources.ProvideString
import java.time.LocalDate

/**
 * @author Danil Glazkov on 14.03.2023, 19:22
 */
interface BirthdayNotificationMapper : BirthdayDomain.Mapper<BirthdayNotification> {

    class Base(
        resources: ProvideString,
        private val nextEvent: CalculateNextEvent,
        private val eventIsToday: EventIsToday,
        private val dateDifference: DateDifference.Days,
    ) : BirthdayNotificationMapper {

        private val birthdayTodayContent by lazy {
            resources.string(R.string.notification_birthday_today)
        }

        private val birthdayInAWeekContent by lazy {
            resources.string(R.string.notification_birthday_in_a_week)
        }

        override fun map(id: Int, name: String, date: LocalDate, type: BirthdayType): BirthdayNotification {
            val birthday: LocalDate = nextEvent.nextEvent(date)

            return when {
                eventIsToday.isToday(birthday) -> BirthdayNotification.ReminderMaxPriority(
                    id, name, birthdayTodayContent
                )
                dateDifference.difference(birthday) == 7 -> BirthdayNotification.Reminder(
                    id, name, birthdayInAWeekContent
                )
                else -> BirthdayNotification.Skip
            }
        }
    }
}