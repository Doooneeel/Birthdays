package ru.daniilglazkov.birthdays.sl.core

import androidx.lifecycle.ViewModel

/**
 * @author Danil Glazkov on 10.06.2022, 03:25
 */
interface DependencyContainer {
    fun <VM : ViewModel> module(clazz: Class<VM>): Module<*>

    class Error : DependencyContainer {
        override fun <VM : ViewModel> module(clazz: Class<VM>): Module<*> =
            throw IllegalArgumentException("Unknown class: $clazz")
    }
}