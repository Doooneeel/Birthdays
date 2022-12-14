package ru.daniilglazkov.birthdays.ui.newbirthday

import android.os.Bundle
import android.view.View
import ru.daniilglazkov.birthdays.R
import ru.daniilglazkov.birthdays.databinding.NewBirthdaySheetFragmentBinding
import ru.daniilglazkov.birthdays.ui.newbirthday.about.AboutBirthdateUi
import ru.daniilglazkov.birthdays.ui.core.ErrorMessage
import ru.daniilglazkov.birthdays.ui.core.click.OnDebouncedClickListener
import ru.daniilglazkov.birthdays.ui.main.BaseNotDraggableSheetFragment

/**
 * @author Danil Glazkov on 11.06.2022, 23:55
 */
class NewBirthdaySheetFragment :
    BaseNotDraggableSheetFragment<NewBirthdaySheetFragmentBinding, NewBirthdayViewModel.Base>(
        inflate = NewBirthdaySheetFragmentBinding::inflate,
        viewModelClass = NewBirthdayViewModel.Base::class.java,
    )
{
    override val grappleId: Int = R.id.grapple

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        clearFocusViewList.add(binding.nameTextInputLayout)

        binding.datePicker.setOnDateChangedListener { _, _, _, _ ->
            viewModel.changeDate(binding.datePicker.date)
        }
        binding.nameInputEditText.setOnFocusChangeListener { _, _ ->
            viewModel.clearErrorMessage()
        }
        binding.clearImageButton.setOnClickListener(OnDebouncedClickListener.SlightDelay {
            binding.nameTextInputLayout.clearFocus()
            viewModel.clearErrorMessage()
            viewModel.clearNewBirthday()
        })
        binding.addButton.setOnClickListener(OnDebouncedClickListener.MediumDelay {
            viewModel.changeBirthday(binding.nameInputEditText.text, binding.datePicker.date)
            viewModel.create()
        })
        viewModel.observeAboutBirthday(viewLifecycleOwner) { aboutBirthdateUi: AboutBirthdateUi ->
            aboutBirthdateUi.apply(binding.turnAgeTextView, binding.untilDayTextView)
        }
        viewModel.observe(viewLifecycleOwner) { newBirthdayUi: NewBirthdayUi ->
            newBirthdayUi.apply(binding.nameInputEditText, binding.datePicker)
        }
        viewModel.observeError(viewLifecycleOwner) { errorMessage: ErrorMessage ->
            errorMessage.apply(binding.nameTextInputLayout)
        }
        viewModel.changeDate(binding.datePicker.date)
        viewModel.fetch()
    }

    override fun onPause() {
        viewModel.changeBirthday(binding.nameInputEditText.text, binding.datePicker.date)
        super.onPause()
    }
}