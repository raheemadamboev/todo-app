package xyz.teamgravity.todo.fragment.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import xyz.teamgravity.todo.databinding.FragmentAddEditTaskBinding
import xyz.teamgravity.todo.viewmodel.viewmodel.AddEditTaskViewModel

@AndroidEntryPoint
class AddEditTaskFragment : Fragment() {
    companion object {
        const val RESULT_REQUEST_KEY = "resultRequestKey"
        const val MESSAGE_EXTRA = "messageExtra"
    }

    private var _binding: FragmentAddEditTaskBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<AddEditTaskViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentAddEditTaskBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}