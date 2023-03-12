package ru.daniilglazkov.birthdays.domain.core.text

/**
 * @author Danil Glazkov on 19.09.2022, 00:29
 */
interface AddDelimiter {

    fun add(first: String, second: String): String


    abstract class Abstract(private val delimiter: String) : AddDelimiter {
        override fun add(first: String, second: String): String = when {
            (first.isNotBlank() && second.isBlank()) or (first == second) -> first
            first.isBlank() && second.isNotBlank() -> second
            first.isBlank() && second.isBlank() -> ""
            else ->"$first$delimiter$second"
        }
    }

    class Base(delimiter: String) : Abstract(delimiter)

    class Colon : Abstract(": ")

    class Range : Abstract(" — ")
}