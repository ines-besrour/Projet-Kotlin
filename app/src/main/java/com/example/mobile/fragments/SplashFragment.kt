package com.example.mobile.fragments

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.mobile.R


class SplashFragment : Fragment() {


    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init(view)


        val handler = Handler(Looper.myLooper()!!)
        handler.postDelayed({
            val sharedPref = context?.getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
            val token = sharedPref?.getString("token","")
            if(token==null || token==""){
                navController.navigate(R.id.action_splashFragment_to_signInFragment)
            }else{
                navController.navigate(R.id.action_splashFragment_to_homeFragment)
            }
        }, 2000)
    }

    private fun init(view: View) {
        navController = Navigation.findNavController(view)
    }
}