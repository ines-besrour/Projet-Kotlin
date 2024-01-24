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
import com.example.mobile.databinding.FragmentAddTodoBinding
import com.example.mobile.mvvm.TodosViewModel


class AddTodo : Fragment() {

    private lateinit var navController: NavController
    private lateinit var binding : FragmentAddTodoBinding
    private var todoId: Int? = null
    private var todoNameParam: String? = null
    private var todoDescriptionParam: String? = null
    private var todoStatusParam: String? = null
    private val todosViewModel: TodosViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            todoId = it.getString("todoId")?.toInt()
            todoNameParam =it.getString("todoName")
            todoDescriptionParam =it.getString("todoDescription")
            todoStatusParam =it.getString("todoStatus")

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentAddTodoBinding.inflate(inflater, container, false)
        Log.d("Home binding",binding.toString())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("ttt","on view created")

        init(view)
        if (isUpdatePage()){
            binding.textView2.text="Update Todo"
            binding.todoName.setText(todoNameParam)
            binding.description.setText(todoDescriptionParam)
            // Todo :: initialize spinner
        }

        todosViewModel.getAddedTodo().observe(this.viewLifecycleOwner){
            if (it != null){
                navController.navigate(R.id.action_addTodo_to_homeFragment)
            }
        }

        todosViewModel.getUpdatedTodo().observe(this.viewLifecycleOwner){
            if (it ==true){
                navController.navigate(R.id.action_addTodo_to_todoDetail)
            }
        }

        todosViewModel.getError().observe(this.viewLifecycleOwner){
            if (it != null){
                Toast.makeText(activity, it, Toast.LENGTH_LONG).show()
            }
        }


        binding.submitButton.setOnClickListener {
            val name = binding.todoName.text.toString()
            val description = binding.description.text.toString()
            // Todo :: get Todos
            if(name.isNotEmpty() && description.isNotEmpty()){
                if(isUpdatePage()){
                    todosViewModel.updateTodo(todoId!!,name,description,"")
                }
                todosViewModel.addTodo(name,description)
            }
        }



    }

    private fun isUpdatePage():Boolean{
        return todoId != null && todoNameParam != null && todoDescriptionParam != null && todoStatusParam !=null
    }


    private fun init(view: View) {
        navController = Navigation.findNavController(view)
    }

    override fun onDestroy() {
        Log.d("Add TO DO","Destroyed")
        super.onDestroy()
    }

}