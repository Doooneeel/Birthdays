package ru.daniilglazkov.birthdays.ui.core.view.toggle

import android.content.Context
import android.util.AttributeSet
import ru.daniilglazkov.birthdays.R
import ru.daniilglazkov.birthdays.ui.core.view.AbstractButtonCustomView
import ru.daniilglazkov.birthdays.ui.core.view.AbstractView
import ru.daniilglazkov.birthdays.ui.core.view.ManageAttributeResources
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
    AbstractView.Check,
    Toggle
{
    override val resources = ManageAttributeResources.Base(typedArray)

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

    override fun map(source: Boolean) {
        buttonState = source
        updateView()
    }
}