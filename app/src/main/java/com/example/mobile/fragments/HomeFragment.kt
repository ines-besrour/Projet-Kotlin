package com.example.mobile.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mobile.R
import com.example.mobile.core.SharedPreferencesManager
import com.example.mobile.databinding.FragmentHomeBinding
import com.example.mobile.mvvm.HomeViewModel
import com.example.mobile.utils.adapter.SetOnTodoClickListener
import com.example.mobile.utils.adapter.TaskAdapter


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var navController: NavController
    private val homeViewModel: HomeViewModel by viewModels()


    private lateinit var taskAdapter: TaskAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        Log.d("Home binding",binding.toString())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init(view)
        binding.apply {
            progressBar.visibility=View.VISIBLE
        }
        homeViewModel.getTodos()

        homeViewModel.observeTodos().observe(this.viewLifecycleOwner){
            t->
            if(t!= null){
                binding.apply {
                    progressBar.visibility=View.INVISIBLE
                }
                taskAdapter.setTodoList(t)
            }else{
                binding.apply {
                    progressBar.visibility=View.INVISIBLE
                }
                Log.d("Home Fragment","Error Getting")
            }
        }

        taskAdapter.setOnTodoClickListener(object : SetOnTodoClickListener {
            override fun setOnClickListener(id:Int) {
                navController.navigate(R.id.action_homeFragment_to_todoDetail,Bundle().apply {
                    putInt("todoId",id)
                })
            }
        })

        binding.addTaskBtn.setOnClickListener {
            navController.navigate(R.id.action_homeFragment_to_addTodo)
        }

        binding.logout.setOnClickListener{
            SharedPreferencesManager.clearSharedPref()
            navController.navigate(R.id.action_homeFragment_to_signInFragment)
        }
    }



    private fun init(view: View) {
        navController = Navigation.findNavController(view)
        binding.mainRecyclerView.setHasFixedSize(true)
        binding.mainRecyclerView.layoutManager = LinearLayoutManager(context)

        taskAdapter = TaskAdapter()
        binding.mainRecyclerView.adapter = taskAdapter
    }




}