package ru.daniilglazkov.birthdays.core

/**
 * @author Danil Glazkov on 05.09.2022, 20:52
 */
interface MutableWithDefault<T> : ReadWithDefault<T>, Save<T>