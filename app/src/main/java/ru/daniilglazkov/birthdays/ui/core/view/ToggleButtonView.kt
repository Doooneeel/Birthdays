package ru.daniilglazkov.birthdays.ui.core.view

import android.content.Context
import android.content.res.ColorStateList
import android.os.Bundle
import android.util.AttributeSet
import android.widget.Button
import ru.daniilglazkov.birthdays.R
import ru.daniilglazkov.birthdays.ui.core.view.listener.OnToggleListener
import ru.daniilglazkov.birthdays.ui.core.view.listener.SetOnToggleListener

/**
 * @author Danil Glazkov on 02.10.2022, 18:19
 */
class ToggleButtonView(
    context: Context,
    attributeSet: AttributeSet? = null,
) : AbstractButtonCustomView(
    context,
    attributeSet,
    R.styleable.ToggleButtonView
) , SetOnToggleListener,
    Toggle
{
    override val resources = ManageAttributeResources.Base(typedArray)

    override val handleOnRestoreInstanceState = { bundle: Bundle ->
        buttonState = bundle.getBoolean(TOGGLE_STATE_KEY)
    }
    override val handleOnSaveInstanceState = { bundle: Bundle ->
        bundle.putBoolean(TOGGLE_STATE_KEY, buttonState)
    }

    private val toggleButtonStateEnabled = ToggleButtonState.Base(
        resources.string(R.styleable.ToggleButtonView_enableText),
        resources.colorState(R.styleable.ToggleButtonView_enableBackgroundTint),
        resources.id(R.styleable.ToggleButtonView_enableDrawable)
    )
    private val toggleButtonStateDisabled = ToggleButtonState.Base(
        resources.string(R.styleable.ToggleButtonView_disableText),
        resources.colorState(R.styleable.ToggleButtonView_disableBackgroundTint),
        resources.id(R.styleable.ToggleButtonView_disableDrawable)
    )

    private var buttonState = true
    private var onToggleListener: OnToggleListener? = null

    private var toggleButtonState: ToggleButtonState

    init {
        toggleButtonState = toggleButtonStateEnabled
        background = resources.drawable(R.styleable.ToggleButtonView_baseBackground)
        resources.recycle()

        setOnClickListener { toggle() }
        updateView()
    }

    override fun updateView() {
        toggleButtonState = if (buttonState) toggleButtonStateEnabled else toggleButtonStateDisabled
        toggleButtonState.apply(view = this)
    }

    override fun toggle() {
        buttonState = !buttonState
        onToggleListener?.onToggle(buttonState)
        updateView()
    }

    override fun setOnToggleListener(onToggleListener: OnToggleListener) {
        this.onToggleListener = onToggleListener
    }

    companion object {
        private const val TOGGLE_STATE_KEY = "toggleState"
    }
}

interface Toggle {
    fun toggle()
}

interface ToggleButtonState {
    fun apply(view: Button)

    class Base(
        private val text: String,
        private val backgroundTint: ColorStateList,
        private val drawableId: Int,
    ) : ToggleButtonState {
        override fun apply(view: Button) {
            view.text = text
            view.backgroundTintList = backgroundTint
            view.setCompoundDrawablesWithIntrinsicBounds(drawableId, 0, 0, 0)
        }
    }
}