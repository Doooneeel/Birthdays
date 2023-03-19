package ru.daniilglazkov.birthdays.sl.core

import android.content.Context
import ru.daniilglazkov.birthdays.sl.module.cache.CacheModule

/**
 * @author Danil Glazkov on 16.02.2023, 17:54
 */
interface ProvideInstances {

    fun provideCacheModule(): CacheModule


    class Debug(private val context: Context) : ProvideInstances {
        override fun provideCacheModule(): CacheModule = CacheModule.Debug(context)
    }

    class Release(private val context: Context) : ProvideInstances {
        override fun provideCacheModule(): CacheModule = CacheModule.Release(context)
    }
}