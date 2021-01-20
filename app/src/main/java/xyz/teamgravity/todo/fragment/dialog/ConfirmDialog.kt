package xyz.teamgravity.todo.fragment.dialog

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import xyz.teamgravity.todo.viewmodel.viewmodel.ConfirmViewModel

@AndroidEntryPoint
class ConfirmDialog : DialogFragment() {
    companion object {
        /**
         * Delete all completed tasks
         */
        const val DELETE_COMPLETED_TASK = 1

        /**
         * Delete all tasks
         */
        const val DELETE_ALL_TASKS = 2
    }

    private val viewModel by viewModels<ConfirmViewModel>()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return MaterialAlertDialogBuilder(requireContext())
            .setTitle(viewModel.headerText)
            .setMessage(viewModel.bodyText)
            .setPositiveButton(viewModel.positiveButtonText) { _, _ ->
                viewModel.onPositiveButtonClick(viewModel.code)
            }.setNegativeButton(viewModel.negativeButtonText, null)
            .create()
    }
}