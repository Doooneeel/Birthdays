package ru.daniilglazkov.birthdays.ui.core.resources

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources

/**
 * @author Danil Glazkov on 10.06.2022, 00:44
 */
interface ManageResources : ProvideString, ProvidePreferences, ProvideNumber {

    class Base(private val context: Context) : ManageResources {

        private val resources: Resources = context.resources

        override fun string(id: Int): String = context.getString(id)

        override fun number(id: Int): Int = resources.getInteger(id)

        override fun quantityString(id: Int, value: Int): String =
            resources.getQuantityString(id, value, value)

        override fun preferences(fileName: String): SharedPreferences =
            context.getSharedPreferences(fileName, Context.MODE_PRIVATE)
    }
}