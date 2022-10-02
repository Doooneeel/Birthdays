package ru.daniilglazkov.birthdays.core.resources

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.core.content.res.ResourcesCompat

/**
 * @author Danil Glazkov on 10.06.2022, 00:44
 */
interface ResourceManager : ProvideString, DrawableProvider, ProvidePreferences, ProvideNumber {

    class Base(private val context: Context) : ResourceManager {
        private val resources: Resources = context.resources

        override fun string(id: Int): String = context.getString(id)

        override fun number(id: Int): Int = resources.getInteger(id)

        override fun quantityString(id: Int, value: Int): String {
            return resources.getQuantityString(id, value, value)
        }
        override fun drawable(@DrawableRes id: Int): Drawable {
            return ResourcesCompat.getDrawable(resources, id, context.theme)!!
        }
        override fun preferences(fileName: String): SharedPreferences {
            return context.getSharedPreferences(fileName, Context.MODE_PRIVATE)
        }
    }
}