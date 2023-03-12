package ru.daniilglazkov.birthdays.ui.core.view.recycler

import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView

/**
 * @author Danil Glazkov on 17.11.2022, 20:20
 */
interface RecyclerViewScroll {

    interface Sharp {
        fun sharpScroll(position: Int)
    }

    interface Smooth {
        fun smoothScroll(position: Int)
    }

    interface Combo : Sharp, Smooth


    class Base(
        private val layoutManager: RecyclerView.LayoutManager?,
        private val linearSmoothScroller: LinearSmoothScroller,
    ) : Combo {

        override fun sharpScroll(position: Int) {
            layoutManager?.scrollToPosition(position)
        }

        override fun smoothScroll(position: Int) {
            if (position >= 0 && layoutManager != null) {
                linearSmoothScroller.targetPosition = position
                layoutManager.startSmoothScroll(linearSmoothScroller)
            }
        }
    }
}