package ru.daniilglazkov.birthdays.ui.birthdays.birthdayinfo

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import ru.daniilglazkov.birthdays.databinding.BirthdayFragmentBinding
import ru.daniilglazkov.birthdays.ui.birthdays.BirthdayUi
import ru.daniilglazkov.birthdays.ui.core.ErrorMessage
import ru.daniilglazkov.birthdays.ui.core.view.CustomToast
import ru.daniilglazkov.birthdays.ui.main.BaseSheetFragment

/**
 * @author Danil Glazkov on 10.06.2022, 21:48
 */
class BirthdaySheetFragment(
    private val birthdayId: Int = -1,
) : BaseSheetFragment<BirthdayFragmentBinding, BirthdayViewModel.Base>(
    inflate = BirthdayFragmentBinding::inflate,
    viewModelClass = BirthdayViewModel.Base::class.java
) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.deleteButton.setOnToggleListener { newState: Boolean ->
            viewModel.changeDeleteState(newState.not())
        }
        viewModel.observe(viewLifecycleOwner) { birthdayUi: BirthdayUi ->
            birthdayUi.apply(binding.nameTextView, dateView = binding.dateTextView)
            //todo make views
        }
        viewModel.observeError(viewLifecycleOwner) { error: ErrorMessage ->
            error.apply(CustomToast(requireContext()))
        }
        viewModel.observeDeleteState(viewLifecycleOwner) { deleteState: Boolean ->
            binding.deleteButton.map(deleteState.not())
        }
        viewModel.init(savedInstanceState == null, birthdayId)
        viewModel.fetch()
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)

        viewModel.complete()
    }
}