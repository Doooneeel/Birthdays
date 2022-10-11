package ru.daniilglazkov.birthdays.sl.core

import ru.daniilglazkov.birthdays.ui.core.resources.ManageResources

/**
 * @author Danil Glazkov on 10.06.2022, 17:18
 */
interface ProvideResourcesManager {
    fun resourcesManager(): ManageResources
}