package ru.daniilglazkov.birthdays.sl.core

import android.content.Context
import ru.daniilglazkov.birthdays.ui.core.CoroutineDispatchers
import ru.daniilglazkov.birthdays.ui.core.navigation.Navigation
import ru.daniilglazkov.birthdays.ui.core.resources.ManageResources
import java.util.*

/**
 * @author Danil Glazkov on 10.06.2022, 03:16
 */
interface CoreModule : ProvideManageResources, ProvideNavigation {

    fun locale(): Locale

    fun context(): Context

    fun dispatchers(): CoroutineDispatchers


    class Base(private val context: Context) : CoreModule {

        private val resourcesManager = ManageResources.Base(context)
        private val navigation = Navigation.Base()
        private val dispatchers = CoroutineDispatchers.Base()


        override fun dispatchers(): CoroutineDispatchers = dispatchers

        override fun manageResources(): ManageResources = resourcesManager

        override fun locale(): Locale = Locale.getDefault()

        override fun navigation(): Navigation.Mutable = navigation

        override fun context(): Context = context
    }
}