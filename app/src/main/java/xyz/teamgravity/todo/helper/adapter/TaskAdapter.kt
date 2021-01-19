package xyz.teamgravity.todo.helper.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import xyz.teamgravity.todo.databinding.CardTaskBinding
import xyz.teamgravity.todo.model.TaskModel

class TaskAdapter(private val listener: OnTaskListener) : ListAdapter<TaskModel, TaskAdapter.TaskViewHolder>(DIFF) {
    companion object {
        val DIFF = object : DiffUtil.ItemCallback<TaskModel>() {
            override fun areItemsTheSame(oldItem: TaskModel, newItem: TaskModel) =
                oldItem._id == newItem._id

            override fun areContentsTheSame(oldItem: TaskModel, newItem: TaskModel) =
                oldItem == newItem
        }
    }

    inner class TaskViewHolder(private val binding: CardTaskBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.apply {
                root.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onTaskClick(getItem(position))
                    }
                }

                taskC.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onTaskCheck(getItem(position), taskC.isChecked)
                    }
                }
            }
        }

        fun bind(model: TaskModel) {
            binding.apply {
                taskT.text = model.name
                taskT.paint.isStrikeThruText = model.completed
                taskI.visibility = if (model.important) View.VISIBLE else View.GONE
                taskC.isChecked = model.completed
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(CardTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    interface OnTaskListener {
        fun onTaskClick(task: TaskModel)
        fun onTaskCheck(task: TaskModel, isChecked: Boolean)
    }
}