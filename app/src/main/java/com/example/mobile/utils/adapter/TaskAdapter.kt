package com.example.mobile.utils.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mobile.core.models.Todo
import com.example.mobile.databinding.EachTodoItemBinding
import com.example.mobile.utils.model.ToDoData

class TaskAdapter : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    private var todos: List<Todo> = ArrayList()
    private lateinit var setOnTodoClickListener: SetOnTodoClickListener


    fun setTodoList(todos:List<Todo>){
        this.todos=todos
        notifyDataSetChanged()
    }

    fun setOnTodoClickListener(setOnMealClickListener: SetOnTodoClickListener) {
        this.setOnTodoClickListener = setOnMealClickListener
    }
    class TaskViewHolder(val binding: EachTodoItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding =
            EachTodoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.binding.apply {
            todoTask.text=todos[position].name
        }

        holder.itemView.setOnClickListener {
            setOnTodoClickListener.setOnClickListener(todos[position].id)
        }
    }

    override fun getItemCount(): Int {
        return todos.size
    }


}

interface SetOnTodoClickListener {
    fun setOnClickListener(id: Int)
}