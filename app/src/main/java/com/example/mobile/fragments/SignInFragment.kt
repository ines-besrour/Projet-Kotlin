package com.example.mobile.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.mobile.R
import com.example.mobile.databinding.FragmentSignInBinding
import com.example.mobile.mvvm.AuthViewModel

class SignInFragment : Fragment() {

    private lateinit var navController: NavController
    private lateinit var binding: FragmentSignInBinding
    private val authViewModel: AuthViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)

        authViewModel.getAuthDetails().observe(this.viewLifecycleOwner
        ) { t ->
            if (t != null) {
                val sharedPref = context?.getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
                val editor = sharedPref?.edit()
                editor?.putString("token",t.token)
                editor?.apply()
                navController.navigate(R.id.action_signInFragment_to_homeFragment)
            }else{
                Log.d("Error","From Observer")
            }
        }

        authViewModel.getError().observe(this.viewLifecycleOwner
        ) { t ->
            if (t != null) {
                Toast.makeText(activity, t, Toast.LENGTH_LONG).show()
            }
        }

        binding.textViewSignUp.setOnClickListener {
            navController.navigate(R.id.action_signInFragment_to_signUpFragment)
        }

        binding.nextBtn.setOnClickListener {
            val email = binding.emailEt.text.toString()
            val pass = binding.passEt.text.toString()
            if (email.isNotEmpty() && pass.isNotEmpty()){
                Log.d("email",email)
                Log.d("pass",pass)
                loginUser(email, pass)
            }
            else
                Toast.makeText(context, "Empty fields are not allowed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loginUser(email: String, pass: String) {
        authViewModel.login(email,pass)
    }

    private fun init(view: View) {
        navController = Navigation.findNavController(view)
    }

}