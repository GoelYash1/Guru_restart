package com.axyz.upasthithguru.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.d
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.axyz.upasthithguru.*
import com.axyz.upasthithguru.apidata.LoginRequest
import com.axyz.upasthithguru.databinding.ActivityLoginBinding
import com.axyz.upasthithguru.other.CheckLogin
import com.axyz.upasthithguru.other.EventObserver
import com.axyz.upasthithguru.viewModels.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var binding:ActivityLoginBinding
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        val view = binding.root
        setContentView(view)
        emailEditText = binding.inputUsername
        passwordEditText = binding.inputPassword
        val signup : TextView = binding.textSignup
        signup.setOnClickListener {
            navigateToRegisterActivity()
        }
        val click_login: Button = binding.clickLogin
        click_login.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }
        val forgot_pass: TextView =binding.forgotPass
        forgot_pass.setOnClickListener {
            navigateToUpdatePasswordActivity()
        }
        loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        emailEditText.setText("ys@gmail.com")
        passwordEditText.setText("12345@Aa")

        subscribeToObservers()
    }

    private fun navigateToUpdatePasswordActivity() {
        startActivity(Intent(applicationContext, ForgotPasswordActivity::class.java))
    }

    private fun navigateToRegisterActivity() {
        startActivity(Intent(applicationContext, RegisterActivity::class.java))
    }
    private fun handleSignupFormSubmission() {
        // Get the values of the form fields

        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()

        // Validate the form fields
        if (email.isEmpty()) {
            // Display an error message if the email field is empty
            Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show()
            emailEditText.error = "Please enter your email"
            return
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            // Display an error message if the email is not a valid email address
            Toast.makeText(this, "Please enter a valid email", Toast.LENGTH_SHORT).show()
            emailEditText.error = "Please enter a valid email"
            return
        }
        if (password.isEmpty()) {
            // Display an error message if the password field is empty
            Toast.makeText(this, "Please enter a password", Toast.LENGTH_SHORT).show()
            passwordEditText.error = "Please enter a password"
            return
        }
        if (!isValidPassword(password)) {
            Toast.makeText(
                this,
                "Password must include 1 number,1 Capital, 1 small & 1 special Character",
                Toast.LENGTH_SHORT
            ).show()
            passwordEditText.error =
                "Password must include 1 number,1 Capital, 1 small & 1 special Character"
            return
        }

        // Clear any error messages
        emailEditText.error = null
        passwordEditText.error = null

        d("Signup Submit ", "Signup $email $password")
        val loginUser = LoginRequest(email, password)
        loginViewModel.login(email,password)

    }

    private fun subscribeToObservers() {
        loginViewModel.signupStatus.observe(this, EventObserver(
            onError = {
//                binding.email.isVisible = false
//                binding.error.isVisible = true
                d("LoginActivity", "Error: $it")
            },
            onLoading = {
//                binding.loginTitle.isVisible = false
//                binding.loginButton.isVisible = false
//                binding.email.isVisible = false
//                binding.password.isVisible = false
//                binding.progressBar2.isVisible = true
                d("LoginActivity", "Loading")
            }
        ) { user ->
//            binding.loginTitle.isVisible = true
//            binding.loginButton.isVisible = true
//            binding.email.isVisible = false
//            binding.password.isVisible = false
//            binding.progressBar2.isVisible = false
            d("LoginActivity", "Success: $user")
            Intent(this, MainActivity::class.java).also {
                startActivity(it)
//                finish()
            }
        })
    }
}