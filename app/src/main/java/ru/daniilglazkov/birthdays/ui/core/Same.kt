package ru.daniilglazkov.birthdays.ui.core

/**
 * @author Danil Glazkov on 12.06.2022, 18:52
 */
interface Same<T> {

    fun same(data: T): Boolean

    fun sameContent(data: T): Boolean

}