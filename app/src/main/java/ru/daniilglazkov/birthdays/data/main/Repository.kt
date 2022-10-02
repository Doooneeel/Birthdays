package ru.daniilglazkov.birthdays.data.main

import ru.daniilglazkov.birthdays.core.Mutable
import ru.daniilglazkov.birthdays.core.MutableWithDefault

/**
 * @author Danil Glazkov on 08.09.2022, 19:23
 */

interface Repository<T> : MutableWithDefault<T> {

    abstract class Abstract<C : Mutable<A>, A, D>(
        private val cacheDataSource: C,
    ) : Repository<D> {
        protected abstract fun toDomain(data: A): D
        protected abstract fun toData(domain: D): A

        override fun read(default: D): D {
            return try {
                toDomain(cacheDataSource.read())
            } catch (exception: Exception) {
                exception.printStackTrace()
                default
            }
        }
        override fun save(data: D) = try {
            cacheDataSource.save(toData(data))
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
    }

}

