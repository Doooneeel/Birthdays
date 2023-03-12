package ru.daniilglazkov.birthdays.ui.core.view.toggle

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import ru.daniilglazkov.birthdays.R
import ru.daniilglazkov.birthdays.ui.core.view.*

/**
 * @author Danil Glazkov on 02.10.2022, 18:19
 */
class ToggleButtonView(
    context: Context,
    attributeSet: AttributeSet? = null,
) : AppCompatButton(
    context,
    attributeSet,
) , SetOnToggleListener,
    AbstractView.Check,
    UpdateView
{
    private val manageResources = ManageAttributeResources.Base(
        context.obtainStyledAttributes(attributeSet, R.styleable.ToggleButtonView)
    )

    private val enabledState = ToggleButtonState.Enable(manageResources)
    private val disabledState = ToggleButtonState.Disable(manageResources)

    private var buttonState: Boolean
    private var onToggleListener: OnToggleListener = OnToggleListener.Unit

    init {
        buttonState = manageResources.boolean(R.styleable.ToggleButtonView_initState, true)
        background = manageResources.drawable(R.styleable.ToggleButtonView_baseBackground)

        setOnClickListener {
            buttonState = !buttonState
            onToggleListener.onToggle(buttonState)
            updateView()
        }
        updateView()
        manageResources.recycle()
    }

    override fun updateView() {
        val toggleButtonState = if (buttonState) enabledState else disabledState
        toggleButtonState.apply(view = this)
    }

    override fun setOnToggleListener(onToggleListener: OnToggleListener) {
        this.onToggleListener = onToggleListener
    }

    override fun map(source: Boolean) {
        if (source != buttonState) {
            buttonState = source
            updateView()
        }
    }
}