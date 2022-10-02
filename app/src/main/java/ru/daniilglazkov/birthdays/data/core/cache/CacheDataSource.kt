package ru.daniilglazkov.birthdays.data.core.cache

import ru.daniilglazkov.birthdays.core.HandleException
import ru.daniilglazkov.birthdays.core.Mutable
import ru.daniilglazkov.birthdays.data.core.EntityToDataMapper
import ru.daniilglazkov.birthdays.data.core.PreferencesDataStore
import ru.daniilglazkov.birthdays.data.core.Serializable
import ru.daniilglazkov.birthdays.data.database.BaseDataAccess

/**
 * @author Danil Glazkov on 11.09.2022, 20:43
 */
interface CacheDataSource<T> : Mutable<T> {

    abstract class AbstractDatabase<D, E : EntityToDataMapper<D>, A : BaseDataAccess<E>>(
        protected val dataAccess: A,
        private val handleException: HandleException
    ) : CacheDataSource<D> {
        protected abstract fun dataToEntity(data: D): E
        abstract fun getDataFromDatabase(): E?

        override fun save(data: D) = dataAccess.insert(dataToEntity(data))

        override fun read(): D = try {
            checkNotNull(getDataFromDatabase()).mapToData()
        } catch (exception: Exception) {
            exception.printStackTrace()
            throw handleException.handle(exception)
        }
    }

    abstract class AbstractPreferences<T>(
        private val preferences: PreferencesDataStore<String>,
        private val serializable: Serializable,
    ) : CacheDataSource<T> {
        protected abstract val type: Class<T>
        protected abstract val key: String

        override fun save(data: T) = preferences.save(key, serializable.toJson(data))
        override fun read(): T = serializable.fromJson(preferences.read(key), type)
    }
}