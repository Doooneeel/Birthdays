package ru.daniilglazkov.birthdays.core.resources

import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes

/**
 * @author Danil Glazkov on 10.06.2022, 00:48
 */
interface DrawableProvider {
    fun drawable(@DrawableRes id: Int): Drawable
}