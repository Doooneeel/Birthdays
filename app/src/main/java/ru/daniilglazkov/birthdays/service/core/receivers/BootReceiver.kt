package ru.daniilglazkov.birthdays.service.core.receivers

import android.content.Context
import android.content.Intent
import android.content.Intent.*

/**
 * @author Danil Glazkov on 19.03.2023, 14:49
 */
class BootReceiver : AbstractReceiver() {

    override val expectedActions = listOf(ACTION_REBOOT, ACTION_BOOT_COMPLETED)

    override suspend fun handle(context: Context, intent: Intent) {
        val application = context.applicationContext as ProvideReceiverWrapper

        application.provideReceiverWrapper()
            .start()
    }
}