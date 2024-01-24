package com.example.mobile.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.mobile.R
import com.example.mobile.core.models.Todo
import com.example.mobile.databinding.FragmentTodoDetailBinding
import com.example.mobile.mvvm.TodoDetailViewModel


class TodoDetail : Fragment() {

    private lateinit var navController: NavController
    private lateinit var binding : FragmentTodoDetailBinding
    private var todoId: Int=0
    private val todoDetailViewModel: TodoDetailViewModel by viewModels()
    private var todo: Todo?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            todoId = it.getInt("todoId")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding= FragmentTodoDetailBinding.inflate(inflater, container, false)
        Log.d("Test","Test From On Create View")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
        showLoading()
        Log.d("Test","Test From On View Created")
        todoDetailViewModel.getTodoById(todoId)

        todoDetailViewModel.getTodo().observe(this.viewLifecycleOwner){
            if(it != null){
                todo=it
                // TODO :: Add here the binding between the data and view
                // Example :
                // binding.todoName = it.name
            }
            hideLoading()
        }

        todoDetailViewModel.getError().observe(this.viewLifecycleOwner){
            if (it!=null){
                Toast.makeText(activity, it, Toast.LENGTH_LONG).show()
            }
            hideLoading()
        }

        todoDetailViewModel.getDeletedTodo().observe(this.viewLifecycleOwner){
            hideLoading()
            if(it==true){
                navController.navigate(R.id.action_todoDetail_to_homeFragment)
            }
        }

        binding.imageView5.setOnClickListener {
            todoDetailViewModel.deleteTodoById(todoId)
        }

        binding.button.setOnClickListener {
            if (todo !=null){
                navController.navigate(R.id.action_todoDetail_to_addTodo,Bundle().apply {
                    putString("todoId",todo!!.id.toString())
                    putString("todoName",todo!!.name)
                    putString("todoDescription",todo!!.description)
                    putString("todoStatus",todo!!.statut)
                })
            }
        }

    }

    private fun init(view: View) {
        navController = Navigation.findNavController(view)
    }

    private fun showLoading(){
        // TODO :: ADD here the visibility toggling to hide content and show loading
        binding.apply {
            imageView5.visibility=View.INVISIBLE
            textView2.visibility=View.INVISIBLE
            progressBar2.visibility=View.VISIBLE
        }
    }

    private fun hideLoading(){
        // TODO :: ADD here the visibility toggling to hide loading and show content
        binding.apply {
            imageView5.visibility=View.VISIBLE
            textView2.visibility=View.VISIBLE
            progressBar2.visibility=View.INVISIBLE
        }

    }


}