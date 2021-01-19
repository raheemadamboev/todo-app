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

    private val viewModel by viewModels<ConfirmViewModel>()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return MaterialAlertDialogBuilder(requireContext())
            .setTitle(viewModel.headerText)
            .setMessage(viewModel.bodyText)
            .setPositiveButton(viewModel.positiveButtonText) { _, _ ->

            }.setNegativeButton(viewModel.negativeButtonText, null)
            .create()
    }
}