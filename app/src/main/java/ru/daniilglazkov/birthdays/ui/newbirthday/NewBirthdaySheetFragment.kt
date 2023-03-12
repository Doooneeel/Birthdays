package ru.daniilglazkov.birthdays.ui.newbirthday

import android.os.Bundle
import android.view.View
import ru.daniilglazkov.birthdays.databinding.NewBirthdaySheetFragmentBinding
import ru.daniilglazkov.birthdays.ui.core.ErrorMessage
import ru.daniilglazkov.birthdays.ui.core.view.click.DebouncedOnClickListener
import ru.daniilglazkov.birthdays.ui.core.view.listener.NonDraggableOnTouchListener
import ru.daniilglazkov.birthdays.ui.core.view.picker.OnLocalDateChangeListener
import ru.daniilglazkov.birthdays.ui.main.BaseSheetFragment
import ru.daniilglazkov.birthdays.ui.newbirthday.about.AboutBirthdateUi
import java.time.LocalDate

/**
 * @author Danil Glazkov on 11.06.2022, 23:55
 */
class NewBirthdaySheetFragment :
    BaseSheetFragment<NewBirthdaySheetFragmentBinding, NewBirthdayViewModel.Base>(
        inflate = NewBirthdaySheetFragmentBinding::inflate,
        viewModelClass = NewBirthdayViewModel.Base::class.java,
    )
{
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.root.setOnClickListener {
            binding.nameTextInputLayout.clearFocus()
            hideKeyboard(binding.nameTextInputLayout.windowToken)
        }

        binding.datePicker.setOnTouchListener(NonDraggableOnTouchListener(changeDragState = this))

        binding.nameInputEditText.setOnFocusChangeListener { _, _ ->
            viewModel.hideErrorMessage()
        }

        binding.clearImageButton.setOnClickListener(DebouncedOnClickListener.SlightDelay {
            binding.nameTextInputLayout.clearFocus()
            viewModel.hideErrorMessage()
            viewModel.clearNewBirthday()
        })

        binding.datePicker.setOnDateChangedListener(OnLocalDateChangeListener { date: LocalDate ->
            viewModel.changeDate(date)
        })

        binding.addButton.setOnClickListener(DebouncedOnClickListener.MediumDelay {
            viewModel.changeBirthday(binding.nameInputEditText.text, binding.datePicker.date)
            viewModel.create()
        })

        viewModel.observeAboutBirthday(viewLifecycleOwner) { aboutBirthdateUi: AboutBirthdateUi ->
            aboutBirthdateUi.apply(binding.turnAgeTextView, binding.untilDayTextView)
        }

        viewModel.observeError(viewLifecycleOwner) { errorMessage: ErrorMessage ->
            errorMessage.apply(binding.nameTextInputLayout)
        }

        viewModel.observeNewBirthday(viewLifecycleOwner) { newBirthdayUi: NewBirthdayUi ->
            newBirthdayUi.apply(binding.nameInputEditText, binding.datePicker)
        }

        viewModel.fetch()
        viewModel.changeDate(binding.datePicker.date)
    }

    override fun onPause() {
        viewModel.changeBirthday(binding.nameInputEditText.text, binding.datePicker.date)
        super.onPause()
    }
}