package ru.daniilglazkov.birthdays.service.core.receivers

import android.content.Context
import android.content.Intent
import android.content.Intent.*

/**
 * @author Danil Glazkov on 19.03.2023, 15:02
 */
class DateTimeChangeReceiver : AbstractReceiver() {

    override val expectedActions = listOf(
        ACTION_DATE_CHANGED,
        ACTION_TIME_CHANGED,
        ACTION_TIMEZONE_CHANGED
    )

    override suspend fun handle(context: Context, intent: Intent) {
        val application = context.applicationContext as ProvideReceiverWrapper

        application.provideReceiverWrapper()
            .start()
    }
}