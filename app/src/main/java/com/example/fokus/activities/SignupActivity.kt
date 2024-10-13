package com.example.fokus.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.fokus.api.APIService
import com.example.fokus.api.RetrofitClient
import com.example.fokus.models.SignupResponse
import com.example.fokus.R
import com.example.fokus.api.saveToken
import com.example.fokus.api.saveUser
import retrofit2.*

class SignupActivity : AppCompatActivity() {
    private lateinit var sUser: EditText
    private lateinit var sEmail: EditText
    private lateinit var sPass: EditText
    private lateinit var signUpBtn: Button
    private lateinit var loginBtnT: TextView
    private lateinit var apiService: APIService
    private val save = saveUser()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_signup)

        apiService = RetrofitClient.create(APIService::class.java)
        sUser = findViewById(R.id.sUser)
        sEmail = findViewById(R.id.sEmail)
        sPass = findViewById(R.id.sPass)
        signUpBtn = findViewById(R.id.sBtn)
        loginBtnT = findViewById(R.id.loginD)

        // Validate input if signUp button is clicked
        signUpBtn.setOnClickListener {
            val username = sUser.text.toString().trim()
            val email = sEmail.text.toString().trim()
            val password = sPass.text.toString().trim()
            // Create account if credentials are valid
            if (validateUser() && validateEmail() && validatePass()) {
                createAccount(username, password, email)
            }
        }

        // Move to login activity if clicked
        loginBtnT.setOnClickListener{
            logInClick()
        }

    }

    private fun validateUser(): Boolean {
        val input = sUser.text.toString()

        // Return false if username input is empty
        if (input.isEmpty()) {
            Toast.makeText(applicationContext, "Please enter a username", Toast.LENGTH_LONG).show()
            return false
        }

        // Return true if not empty
        return true
    }

    private fun validateEmail(): Boolean {
        val input = sEmail.text.toString()

        // Return false if email address is empty or invalid
        if (input.isEmpty()) {
            Toast.makeText(applicationContext, "Please enter an e-mail address", Toast.LENGTH_LONG).show()
            return false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(input).matches()) {
            Toast.makeText(applicationContext, "Invalid e-mail address", Toast.LENGTH_LONG).show()
            return false
        }

        // Return true if not empty or invalid
        return true
    }

    private fun validatePass(): Boolean {
        val input = sPass.text.toString()

        // Return false if password input is empty or less than 8 characters
        if (input.isEmpty()) {
            Toast.makeText(applicationContext, "Please enter a password", Toast.LENGTH_LONG).show()
            return false
        } else if (input.length < 8) {
            Toast.makeText(applicationContext, "Your password must be at least 8 characters", Toast.LENGTH_LONG).show()
            return false
        }

        // Return true if password is valid
        return true
    }

    private fun createAccount(username: String, password: String, email: String) {
        // Send signup request using apiService
        apiService.signup(username, password, email).enqueue(object : Callback<SignupResponse> {
            // Modify response function
            override fun onResponse(call: Call<SignupResponse>, response: Response<SignupResponse>) {
                if (response.isSuccessful) {
                    val signupResponse = response.body()
                    val token = signupResponse?.token
                    val idFromResponse = signupResponse?.data?.id
                    val usernameFromResponse = signupResponse?.data?.username
                    val emailFromResponse = signupResponse?.data?.email

                    // Save token if not null
                    if (token != null && idFromResponse != null && usernameFromResponse != null
                        && emailFromResponse != null) {
                        saveToken(applicationContext, token)
                        RetrofitClient.setToken(token)
                        save.saveId(applicationContext, idFromResponse)
                        save.saveUsername(applicationContext, usernameFromResponse)
                        save.saveEmail(applicationContext, emailFromResponse)
                        save.savePass(applicationContext, password)

                        Toast.makeText(this@SignupActivity, signupResponse.message, Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@SignupActivity, MainActivity::class.java)
                        startActivity(intent)
                    }
                } else {
                    val errorResponse = response.errorBody()?.string()
                    Toast.makeText(this@SignupActivity, "Signup Failed: $errorResponse", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<SignupResponse>, t: Throwable) {
                Toast.makeText(this@SignupActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // Move to login activity if clicked
    private fun logInClick() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}