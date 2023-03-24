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
        private val resources: ProvideString,
        private val manageStatus: ManageNotificationDisplayStatus,
        private val nextEvent: CalculateNextEvent,
        private val eventIsToday: EventIsToday,
        private val dateDifference: DateDifference.Days,
        private val nextMapper: BirthdayNotificationMapper = Skip,
    ) : BirthdayNotificationMapper {
        override fun map(id: Int, name: String, date: LocalDate, type: BirthdayType): BirthdayNotification {
            val birthdayTodayKey = "notification_birthday_today_$id"
            val birthdayTomorrow = "notification_birthday_tomorrow_$id"
            val birthdayInAWeekKey = "notification_birthday_in_a_week_$id"

            val keys = listOf(birthdayTodayKey, birthdayTomorrow, birthdayInAWeekKey)

            val birthday: LocalDate = nextEvent.nextEvent(date)
            val daysBeforeBirthday: Int = dateDifference.difference(birthday)

            return when {
                eventIsToday.isToday(birthday) -> manageStatus.fetchBasedOnStatus(
                    BirthdayNotification.ReminderMaxPriority(id, name,
                        resources.string(R.string.notification_birthday_today)
                    ), birthdayTodayKey, keys
                )
                daysBeforeBirthday == 1 -> manageStatus.fetchBasedOnStatus(
                    BirthdayNotification.ReminderMaxPriority(id, name,
                        resources.string(R.string.notification_birthday_tomorrow)
                    ), birthdayTomorrow, keys
                )
                daysBeforeBirthday == 7 -> manageStatus.fetchBasedOnStatus(
                    BirthdayNotification.Reminder(id, name,
                        resources.string(R.string.notification_birthday_in_a_week)
                    ), birthdayInAWeekKey, keys
                )
                else -> {
                    manageStatus.resetStatus(keys)
                    nextMapper.map(id, name, date, type)
                }
            }
        }
    }

    object Skip : BirthdayNotificationMapper {
        override fun map(id: Int, name: String, date: LocalDate, type: BirthdayType) =
            BirthdayNotification.Skip
    }
}