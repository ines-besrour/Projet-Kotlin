package com.example.mobile.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.mobile.R
import com.example.mobile.databinding.FragmentSignUpBinding
import com.example.mobile.mvvm.AuthViewModel


class SignUpFragment : Fragment() {

    private lateinit var navController: NavController
    private lateinit var binding: FragmentSignUpBinding
    private val authViewModel: AuthViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        init(view)

        binding.textViewSignIn.setOnClickListener {
            navController.navigate(R.id.action_signUpFragment_to_signInFragment)
        }

        authViewModel.getIsSignedSuccessfully().observe(this.viewLifecycleOwner){
            t->
                if(t==true){
                    navController.navigate(R.id.action_signUpFragment_to_signInFragment)
                }
        }
        authViewModel.getError().observe(this.viewLifecycleOwner){
                t->
            if(t != null){
                Toast.makeText(activity, t, Toast.LENGTH_LONG).show()
            }
        }

        binding.nextBtn.setOnClickListener {
            val email = binding.emailEt.text.toString()
            val pass = binding.passEt.text.toString()
            val verifyPass = binding.verifyPassEt.text.toString()


            if (email.isNotEmpty() && pass.isNotEmpty() && verifyPass.isNotEmpty()) {
                if (pass == verifyPass) {
                    registerUser(email, pass)
                } else {
                    Toast.makeText(context, "Password is not same", Toast.LENGTH_SHORT).show()
                }
            } else
                Toast.makeText(context, "Empty fields are not allowed", Toast.LENGTH_SHORT).show()
        }

    }

    private fun registerUser(email: String, pass: String) {
        authViewModel.signUp(email,pass)
    }

    private fun init(view: View) {
        navController = Navigation.findNavController(view)
    }
}