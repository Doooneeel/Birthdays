package ru.daniilglazkov.birthdays.service.intents

import android.content.Context
import android.content.Intent
import ru.daniilglazkov.birthdays.ui.main.MainActivity

/**
 * @author Danil Glazkov on 16.03.2023, 21:15
 */
class MainActivityIntent(context: Context) : Intent(context, MainActivity::class.java) {
    init {
        flags = FLAG_ACTIVITY_SINGLE_TOP
    }
}