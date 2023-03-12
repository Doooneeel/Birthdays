package ru.daniilglazkov.birthdays.ui.core.view.recycler

import android.content.Context
import android.util.DisplayMetrics
import androidx.recyclerview.widget.LinearSmoothScroller

/**
 * @author Danil Glazkov on 17.11.2022, 19:16
 */
class BaseLinearSmoothScroller(
    context: Context,
    private val scrollDuration: Float = 25f
) : LinearSmoothScroller(context) {
    override fun getVerticalSnapPreference(): Int = SNAP_TO_START

    override fun calculateSpeedPerPixel(displayMetrics: DisplayMetrics): Float =
        scrollDuration / displayMetrics.densityDpi
}