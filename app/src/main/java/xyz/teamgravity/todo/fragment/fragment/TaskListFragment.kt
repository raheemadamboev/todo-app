package xyz.teamgravity.todo.fragment.fragment

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import xyz.teamgravity.todo.R
import xyz.teamgravity.todo.presentation.activity.Main
import xyz.teamgravity.todo.databinding.FragmentTaskListBinding
import xyz.teamgravity.todo.fragment.dialog.ConfirmDialog
import xyz.teamgravity.todo.helper.adapter.TaskAdapter
import xyz.teamgravity.todo.core.extension.exhaustive
import xyz.teamgravity.todo.core.extension.onQueryTextChanged
import xyz.teamgravity.todo.data.model.TodoModel
import xyz.teamgravity.todo.core.util.TodoSort
import xyz.teamgravity.todo.viewmodel.viewmodel.TaskListViewModel

@AndroidEntryPoint
class TaskListFragment : Fragment(), TaskAdapter.OnTaskListener {

    private var _binding: FragmentTaskListBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<TaskListViewModel>()

    private lateinit var searchView: SearchView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentTaskListBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        recyclerView()
        button()
        events()
        result()
    }

    private fun recyclerView() {
        val adapter = TaskAdapter(this)

        binding.apply {
            recyclerView.setHasFixedSize(true)
            recyclerView.adapter = adapter

            ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                // swipe
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    viewModel.onTaskSwiped(adapter.currentList[viewHolder.adapterPosition])
                }
            }).attachToRecyclerView(recyclerView)
        }

        // observer data
        viewModel.tasks.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    private fun button() {
        onAdd()
    }

    private fun events() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.taskEvent.collect { event ->
                when (event) {
                    is TaskListViewModel.TaskEvent.ShowUndoDeleteTaskMessage -> {
                        Snackbar.make(requireView(), R.string.deleted_successfully, Snackbar.LENGTH_LONG)
                            .setAction(R.string.undo) {
                                viewModel.onUndoDeleteTaskClick(event.task)
                            }.show()
                    }

                    is TaskListViewModel.TaskEvent.NavigateToAddTaskScreen -> {
                        findNavController().navigate(
                            TaskListFragmentDirections.actionTaskListFragmentToAddEditTaskFragment(
                                null,
                                getString(R.string.new_task)
                            )
                        )
                    }

                    is TaskListViewModel.TaskEvent.NavigateToAddEditTaskScreen -> {
                        findNavController().navigate(
                            TaskListFragmentDirections.actionTaskListFragmentToAddEditTaskFragment(
                                event.task,
                                getString(R.string.edit_task)
                            )
                        )
                    }

                    is TaskListViewModel.TaskEvent.ShowAddEditResultMessage -> {
                        Snackbar.make(requireView(), event.message, Snackbar.LENGTH_SHORT).show()
                    }

                    is TaskListViewModel.TaskEvent.NavigateDeleteAllCompleted -> {
                        findNavController().navigate(
                            TaskListFragmentDirections.actionGlobalConfirmDialog(
                                header = getString(R.string.confirm_deletion),
                                body = getString(R.string.wanna_delete_completed),
                                positiveButton = getString(R.string.yes),
                                negativeButton = getString(R.string.no),
                                code = ConfirmDialog.DELETE_COMPLETED_TASK
                            )
                        )
                    }

                    is TaskListViewModel.TaskEvent.NavigateDeleteAllTasks -> {
                        findNavController().navigate(
                            TaskListFragmentDirections.actionGlobalConfirmDialog(
                                header = getString(R.string.confirm_deletion),
                                body = getString(R.string.wanna_delete_all),
                                positiveButton = getString(R.string.yes),
                                negativeButton = getString(R.string.no),
                                code = ConfirmDialog.DELETE_ALL_TASKS
                            )
                        )
                    }

                    is TaskListViewModel.TaskEvent.NavigateAboutMeScreen -> {
                        findNavController().navigate(TaskListFragmentDirections.actionTaskListFragmentToAboutFragment())
                    }

                    is TaskListViewModel.TaskEvent.ChangeLanguage -> {
                        val intent = Intent(requireContext(), Main::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        startActivity(intent)
                    }
                }.exhaustive
            }
        }
    }

    private fun result() {
        setFragmentResultListener(AddEditTaskFragment.RESULT_REQUEST_KEY) { _, bundle ->
            val result = bundle.getString(AddEditTaskFragment.MESSAGE_EXTRA, "")
            viewModel.onAddEditTaskResult(result)
        }
    }

    // add button
    private fun onAdd() {
        binding.addB.setOnClickListener {
            viewModel.onAddButtonClick()
        }
    }

    // task click
    override fun onTaskClick(task: TodoModel) {
        viewModel.onTaskCardClick(task)
    }

    // task check click
    override fun onTaskCheck(task: TodoModel, isChecked: Boolean) {
        viewModel.onTaskCardChecked(task, isChecked)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.task_list_menu, menu)

        val searchItem = menu.findItem(R.id.action_search)
        searchView = searchItem.actionView as SearchView

        // get back query
        val pendingQuery = viewModel.query.value
        if (!pendingQuery.isNullOrBlank()) {
            searchItem.expandActionView()
            searchView.setQuery(pendingQuery, false)
        }

        // text change
        searchView.onQueryTextChanged {
            viewModel.query.value = it
        }

        // update hide completed when app starts
        viewLifecycleOwner.lifecycleScope.launch {
            // it gets only first and cancels coroutine
            menu.findItem(R.id.action_hide).isChecked =
                viewModel.preferencesFlow.first().hideCompleted

            // update language
            when (viewModel.preferencesFlow.first().language) {
                "ru" -> menu.findItem(R.id.action_russian).isChecked = true
                "uz" -> menu.findItem(R.id.action_uzbek).isChecked = true
                else -> menu.findItem(R.id.action_english).isChecked = true
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_sort_name -> {
                viewModel.onMenuTaskSort(TodoSort.BY_NAME)
                true
            }

            R.id.action_sort_date -> {
                viewModel.onMenuTaskSort(TodoSort.BY_DATE)
                true
            }

            R.id.action_hide -> {
                item.isChecked = !item.isChecked
                viewModel.onMenuHideCompleted(item.isChecked)
                true
            }

            R.id.action_delete_completed -> {
                viewModel.onMenuDeleteAllCompleted()
                true
            }

            R.id.action_delete_all -> {
                viewModel.onMenuDeleteAllTasks()
                true
            }

            R.id.action_english -> {
                viewModel.onMenuLanguage("en")
                true
            }

            R.id.action_russian -> {
                viewModel.onMenuLanguage("ru")
                true
            }

            R.id.action_uzbek -> {
                viewModel.onMenuLanguage("uz")
                true
            }

            R.id.action_about_me -> {
                viewModel.onMenuAboutMe()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        if (this::searchView.isInitialized) searchView.setOnQueryTextListener(null)
    }
}