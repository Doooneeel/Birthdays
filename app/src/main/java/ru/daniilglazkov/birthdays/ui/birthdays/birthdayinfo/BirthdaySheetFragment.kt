package ru.daniilglazkov.birthdays.ui.birthdays.birthdayinfo

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import ru.daniilglazkov.birthdays.databinding.BirthdayFragmentBinding
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

        binding.deleteButton.setOnToggleListener { newState ->
            viewModel.changeStatus(newState.not())
        }
        viewModel.observe(viewLifecycleOwner) { birthdayUi ->
            //Todo make views
        }
        viewModel.observeError(viewLifecycleOwner) { error ->
            error.apply(CustomToast(requireContext()))
        }
        viewModel.init(savedInstanceState == null, birthdayId)
        viewModel.fetch()
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        viewModel.dismiss()
    }
}