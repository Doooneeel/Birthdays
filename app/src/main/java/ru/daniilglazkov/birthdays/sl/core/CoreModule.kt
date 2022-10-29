package ru.daniilglazkov.birthdays.sl.core

import android.content.Context
import android.content.SharedPreferences
import ru.daniilglazkov.birthdays.ui.core.navigation.Navigation
import ru.daniilglazkov.birthdays.ui.core.resources.ProvidePreferences
import ru.daniilglazkov.birthdays.ui.core.resources.ManageResources
import java.time.LocalDate

/**
 * @author Danil Glazkov on 10.06.2022, 03:16
 */
interface CoreModule : ProvideResourcesManager, ProvidePreferences, ProvideNavigation, ProvideCurrentDate {

    class Base(private val context: Context) : CoreModule {
        private val now = LocalDate.now()
        private val resourcesManager = ManageResources.Base(context)
        private val navigation = Navigation.Base()

        override fun provideCurrentDate(): LocalDate = now

        override fun resourcesManager(): ManageResources = resourcesManager

        override fun preferences(fileName: String): SharedPreferences {
            return context.getSharedPreferences(fileName, Context.MODE_PRIVATE)
        }
        override fun navigation(): Navigation.Mutable = navigation
    }
}