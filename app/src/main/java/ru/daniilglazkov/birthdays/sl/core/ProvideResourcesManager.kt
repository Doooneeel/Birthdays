package ru.daniilglazkov.birthdays.sl.core

import ru.daniilglazkov.birthdays.core.resources.ResourceManager

/**
 * @author Danil Glazkov on 10.06.2022, 17:18
 */
interface ProvideResourcesManager {
    fun resourcesManager(): ResourceManager
}