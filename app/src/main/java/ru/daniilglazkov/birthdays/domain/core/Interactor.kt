package ru.daniilglazkov.birthdays.domain.core

/**
 * @author Danil Glazkov on 10.10.2022, 17:55
 */
interface Interactor {

    fun <T> handle(
        successful: (T) -> Unit,
        onFailure: () -> Unit,
        block: () -> T
    )

    abstract class Abstract : Interactor {

        override fun <T> handle(
            successful: (T) -> Unit,
            onFailure: () -> Unit,
            block: () -> T,
        ) = try {
            val result = block.invoke()
            successful.invoke(result)
        } catch (exception: Exception) {
            onFailure.invoke()
        }
    }
}