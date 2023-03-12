package ru.daniilglazkov.birthdays.ui.core.view.listener

import android.view.MotionEvent
import android.view.MotionEvent.ACTION_DOWN
import android.view.View

/**
 * @author Danil Glazkov on 07.11.2022, 13:01
 */
class NonDraggableOnTouchListener(
    private val changeDragState: ChangeDragState,
) : View.OnTouchListener {
    override fun onTouch(view: View, event: MotionEvent): Boolean {
        val isDraggable = event.action != ACTION_DOWN
        changeDragState.changeDragState(isDraggable)
        view.performClick()
        return true
    }
}