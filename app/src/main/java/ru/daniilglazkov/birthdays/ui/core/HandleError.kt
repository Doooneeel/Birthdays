package ru.daniilglazkov.birthdays.ui.core

import ru.daniilglazkov.birthdays.R
import ru.daniilglazkov.birthdays.domain.core.exceptions.EmptyCacheException
import ru.daniilglazkov.birthdays.domain.core.exceptions.NotFoundException
import ru.daniilglazkov.birthdays.ui.core.resources.ProvideString

/**
 * @author Danil Glazkov on 20.02.2023, 23:15
 */
interface HandleError {

    fun handle(exception: Exception): String


    class GenericError(private val resources: ProvideString) : HandleError {
        override fun handle(exception: Exception): String =
            resources.string(R.string.unknown_error)
    }

    class Birthday(
        private val resources: ProvideString,
        private val next: HandleError = GenericError(resources)
    ) : HandleError {
        override fun handle(exception: Exception): String = when(exception) {
            is NotFoundException -> resources.string(R.string.element_not_found)
            else -> next.handle(exception)
        }
    }

    class Base(
        private val resources: ProvideString,
        private val next: HandleError = GenericError(resources)
    ) : HandleError {
        override fun handle(exception: Exception): String {
            return when (exception) {
                is EmptyCacheException -> resources.string(R.string.list_is_empty)
                is NotFoundException -> resources.string(R.string.nothing_found)
                else -> next.handle(exception)
            }
        }
    }

}