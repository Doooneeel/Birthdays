package ru.daniilglazkov.birthdays.ui.core.view.listener

import android.widget.AbsListView
import androidx.recyclerview.widget.RecyclerView

/**
 * @author Danil Glazkov on 18.11.2022, 04:30
 */
abstract class ScrollStateChangeListener : RecyclerView.OnScrollListener() {

    protected open fun fling() = Unit

    protected open fun touchScroll() = Unit

    protected open fun endScroll() = Unit


    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)

        when (newState) {
            AbsListView.OnScrollListener.SCROLL_STATE_FLING -> fling()
            AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL -> touchScroll()
            else -> endScroll()
        }
    }

    class Touch(private val onTouch: () -> Unit) : ScrollStateChangeListener() {
        override fun touchScroll() = onTouch.invoke()
    }
}