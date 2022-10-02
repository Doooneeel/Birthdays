package ru.daniilglazkov.birthdays.ui.core

import android.os.SystemClock

/**
 * @author Danil Glazkov on 23.09.2022, 06:54
 */

interface Debounce {
    fun handle(block: () -> Unit)

    abstract class Abstract(private val delay: Int) : Debounce {
        private var lastInvoke = 0L

        override fun handle(block: () -> Unit) {
            if (SystemClock.elapsedRealtime() - lastInvoke >= delay) {
                lastInvoke = SystemClock.elapsedRealtime()
                block.invoke()
            }
        }
    }

    class SlightDelay : Abstract(delay = 400)
    class MediumDelay : Abstract(delay = 800)
    class LongDelay : Abstract(delay = 1200)

    class NoDelay : Debounce {
        override fun handle(block: () -> Unit) = block.invoke()
    }
}

