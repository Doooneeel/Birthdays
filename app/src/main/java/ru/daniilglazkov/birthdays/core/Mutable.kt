package ru.daniilglazkov.birthdays.core

/**
 * @author Danil Glazkov on 10.06.2022, 22:07
 */
interface Mutable<T> : Read<T>, Save<T>