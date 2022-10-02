package ru.daniilglazkov.birthdays.sl.core

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import ru.daniilglazkov.birthdays.core.resources.ProvidePreferences
import ru.daniilglazkov.birthdays.core.resources.ResourceManager
import ru.daniilglazkov.birthdays.ui.core.Navigation

/**
 * @author Danil Glazkov on 10.06.2022, 03:16
 */
interface CoreModule: ProvideResourcesManager, ProvidePreferences, ProvideNavigation {

    class Base(private val context: Context) : CoreModule {

        private val resourcesManager = ResourceManager.Base(context)
        private val navigation = Navigation.Base()

        override fun resourcesManager(): ResourceManager = resourcesManager

        override fun preferences(fileName: String): SharedPreferences {
            return context.getSharedPreferences(fileName, MODE_PRIVATE)
        }
        override fun navigation(): Navigation.Mutable = navigation
    }
}