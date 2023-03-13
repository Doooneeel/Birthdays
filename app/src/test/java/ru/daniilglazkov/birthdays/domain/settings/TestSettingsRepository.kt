package ru.daniilglazkov.birthdays.domain.settings

import ru.daniilglazkov.birthdays.domain.core.exceptions.EmptyCacheException

/**
 * @author Danil Glazkov on 27.02.2023, 6:59
 */
class TestSettingsRepository : SettingsRepository {

    val saveCalledList = mutableListOf<SettingsDomain>()
    var data: SettingsDomain? = null
    var readCallCount = 0

    override suspend fun read(): SettingsDomain {
        ++readCallCount
        return data ?: throw EmptyCacheException()
    }

    override suspend fun save(data: SettingsDomain) {
        saveCalledList.add(data)
    }
}