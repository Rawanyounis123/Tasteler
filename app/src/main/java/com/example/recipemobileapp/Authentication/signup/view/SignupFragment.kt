package com.example.recipemobileapp.Authentication.signup.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.recipemobileapp.Authentication.signup.SignUpRepo.SignUpRepoImpl
import com.example.recipemobileapp.Authentication.signup.viewModel.SignUpViewModel
import com.example.recipemobileapp.Authentication.signup.viewModel.SignUpViewModelFactory
import com.example.recipemobileapp.Database.User
import com.example.recipemobileapp.Database.localDataSource.LocalDataSourceImpl
import com.example.recipemobileapp.R
import com.google.android.material.textfield.TextInputLayout

class SignupFragment : Fragment() {
    lateinit var email: TextInputLayout
    lateinit var password: TextInputLayout
    lateinit var firstname:TextInputLayout
    lateinit var lastname:TextInputLayout
    lateinit var signupbtn: Button
    lateinit var signUpViewModel: SignUpViewModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_signup, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        gettingViewModelReady(requireContext())
        firstname=view.findViewById(R.id.textinput_first_name)
        lastname=view.findViewById(R.id.textinput_last_name)
        email=view.findViewById(R.id.textinput_email)
        password=view.findViewById(R.id.textinput_password)
        signupbtn=view.findViewById(R.id.button_signup)
        signupbtn.setOnClickListener{
            isValidData(email.editText?.text.toString(),firstname.editText?.text.toString(),lastname.editText?.text.toString(),password.editText?.text.toString())
        }
    }
    private fun gettingViewModelReady(context: Context) {
        val signUpViewModelFactory = SignUpViewModelFactory(
            SignUpRepoImpl(LocalDataSourceImpl(context))
        )
        signUpViewModel = ViewModelProvider(this,signUpViewModelFactory).get(SignUpViewModel::class.java)
    }
    private fun isValidData(email:String,firstname:String,lastname:String,password: String){
        if(isValidName(firstname)&& isValidName(lastname)&& isValidPassword(password) &&isValidEmail(email)){
            signUpViewModel.insertUser(
                User(0, firstname,lastname, email,password))
        }else if(!isValidName(firstname)) {
            Toast.makeText(context, "invalid First Name", Toast.LENGTH_SHORT).show()
        }else if(!isValidName(lastname)){
            Toast.makeText(context, "invalid Last Name", Toast.LENGTH_SHORT).show()
        }else if(!isValidPassword(password)){
            Toast.makeText(context, "invalid password", Toast.LENGTH_SHORT).show()
        }else if(!isValidEmail(email)){
            Toast.makeText(context, "invalid Email", Toast.LENGTH_SHORT).show()
        }
    }
    private fun isValidEmail(email :String):Boolean{
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$"
        return email.matches(emailRegex.toRegex())
    }
    private fun isValidName(name:String):Boolean{
        val nameRegex="^[A-Za-z]{3,30}\$"
        return name.matches(nameRegex.toRegex())
    }
    private fun isValidPassword(password:String):Boolean{
        val passwordRegex="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,8}$"
        return password.matches(passwordRegex.toRegex())
    }
}