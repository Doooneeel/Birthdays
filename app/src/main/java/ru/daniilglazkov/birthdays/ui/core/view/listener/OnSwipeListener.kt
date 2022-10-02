package ru.daniilglazkov.birthdays.ui.core.view.listener

import android.graphics.Point
import android.view.MotionEvent
import android.view.View
import ru.daniilglazkov.birthdays.ui.core.Debounce
import kotlin.math.abs

/**
 * @author Danil Glazkov on 01.10.2022, 02:25
 */
interface OnSwipeListener : View.OnTouchListener {

    abstract class Abstract(
        private val swipeThreshold: Int,
        private val debounce: Debounce,
        private val block: () -> Unit
    ) : OnSwipeListener {
        protected abstract fun thresholdReached(swipePosition: Point, threshold: Int): Boolean

        override fun onTouch(view: View, event: MotionEvent): Boolean {
            if (event.action == MotionEvent.ACTION_MOVE) {
                val eventCenterX = event.x.toInt() - view.width / 2
                val eventCenterY = event.y.toInt() - view.height / 2

                if (thresholdReached(Point(eventCenterX, eventCenterY), swipeThreshold)) {
                    view.performClick()
                    debounce.handle(block)
                }
            }
            return true
        }
    }

    abstract class AbstractVertical(
        baseThreshold: Int,
        private val horizontalThreshold: Int,
        debounce: Debounce,
        block: () -> Unit
    ) : Abstract(baseThreshold, debounce, block) {
        protected abstract fun verticalThresholdReached(swipeY: Int, threshold: Int): Boolean

        override fun thresholdReached(swipePosition: Point, threshold: Int): Boolean =
            abs(swipePosition.x) < horizontalThreshold &&
                    verticalThresholdReached(swipePosition.y, threshold)
    }

    abstract class AbstractHorizontal(
        baseThreshold: Int,
        private val verticalThreshold: Int,
        debounce: Debounce,
        block: () -> Unit
    ) : Abstract(baseThreshold, debounce, block) {
        protected abstract fun horizontalThresholdReached(swipeX: Int, threshold: Int): Boolean

        override fun thresholdReached(swipePosition: Point, threshold: Int) =
            abs(swipePosition.y) < verticalThreshold &&
                    horizontalThresholdReached(swipePosition.x, threshold)
    }

    class Up(
        debounce: Debounce = Debounce.NoDelay(),
        block: () -> Unit,
    ) : AbstractVertical(DEFAULT_THRESHOLD, DEFAULT_HORIZONTAL_THRESHOLD, debounce, block) {
        override fun verticalThresholdReached(swipeY: Int, threshold: Int) =
            swipeY < -threshold
    }

    class Down(
        debounce: Debounce = Debounce.NoDelay(),
        block: () -> Unit,
    ) : AbstractVertical(DEFAULT_THRESHOLD, DEFAULT_HORIZONTAL_THRESHOLD, debounce, block) {
        override fun verticalThresholdReached(swipeY: Int, threshold: Int) =
            swipeY > threshold
    }

    class Right(
        debounce: Debounce = Debounce.NoDelay(),
        block: () -> Unit,
    ) : AbstractHorizontal(DEFAULT_THRESHOLD, DEFAULT_VERTICAL_THRESHOLD, debounce, block) {
        override fun horizontalThresholdReached(swipeX: Int, threshold: Int) =
            swipeX > threshold
    }

    class Left(
        debounce: Debounce = Debounce.NoDelay(),
        block: () -> Unit,
    ) : AbstractHorizontal(DEFAULT_THRESHOLD, DEFAULT_VERTICAL_THRESHOLD, debounce, block) {
        override fun horizontalThresholdReached(swipeX: Int, threshold: Int) =
            swipeX < -threshold
    }

    companion object {
        private const val DEFAULT_THRESHOLD: Int = 200
        private const val DEFAULT_VERTICAL_THRESHOLD: Int = 150
        private const val DEFAULT_HORIZONTAL_THRESHOLD: Int = 200
    }
}