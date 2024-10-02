package com.example.fokus.activities

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.fokus.api.APIService
import com.example.fokus.models.LoginResponse
import com.example.fokus.api.RetrofitClient
import com.example.fokus.R
import com.example.fokus.api.saveToken
import retrofit2.*

class LoginActivity : AppCompatActivity() {
    private lateinit var lEmail: EditText
    private lateinit var lPass: EditText
    private lateinit var loginBtn: Button
    private lateinit var signUp: TextView
    private lateinit var rememberMe: CheckBox
    private lateinit var apiService: APIService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        enableEdgeToEdge()

        val sharedPref = getSharedPreferences("rememberMe", MODE_PRIVATE)
        val sharedPrefEditor = sharedPref.edit()

        apiService = RetrofitClient.create(APIService::class.java)
        lEmail = findViewById(R.id.lEmail)
        lPass = findViewById(R.id.lPass)
        loginBtn = findViewById(R.id.lBtn)
        signUp = findViewById(R.id.signupD)
        rememberMe = findViewById(R.id.rMeCb)

        // Instantly retrieve and load data from sharedPref
        lEmail.setText(sharedPref.getString("email", null))
        lPass.setText(sharedPref.getString("password", null))
        rememberMe.isChecked = sharedPref.getBoolean("isRemembered", false)

        loginBtn.setOnClickListener {
            val email = lEmail.text.toString().trim()
            val password = lPass.text.toString().trim()
            val isRemembered = rememberMe.isChecked

            // Save to sharedPref if rememberMe isChecked
            if (rememberMe.isChecked) {
                sharedPrefEditor.apply {
                    putString("email", email)
                    putString("password", password)
                    putBoolean("isRemembered", isRemembered)
                    apply()
                }
            }

            // Do login function if email and password are valid
            if (validateEmail() && validatePass()) {
                login(email, password)
            }
        }

        // Do signUpClick function if signUp button is clicked
        signUp.setOnClickListener {
            signUpClick()
        }
    }

    private fun validateEmail(): Boolean {
        val input = lEmail.text.toString()

        // Return false if the email input field is empty or invalid
        if (input.isEmpty()) {
            Toast.makeText(applicationContext, "Please enter your e-mail address", Toast.LENGTH_LONG).show()
            return false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(input).matches()) {
            Toast.makeText(applicationContext, "Invalid e-mail address", Toast.LENGTH_LONG).show()
            return false
        }

        // Return true by default
        return true
    }

    private fun validatePass(): Boolean {
        val input = lPass.text.toString()

        // Return false if password is empty
        if (input.isEmpty()) {
            Toast.makeText(applicationContext, "Please enter your password", Toast.LENGTH_LONG).show()
            return false
        }

        // Return true by default
        return true
    }

    private fun login(email: String, password: String) {
        // Asynchronously send login request using apiService
        apiService.login(email, password).enqueue(object : Callback<LoginResponse> {
            // Modify apiService's onResponse function
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    val token = loginResponse?.token

                    // Save token if not null
                    if (token != null) {
                        saveToken(applicationContext, token)
                    }

                    // Set token if not null
                    if (token != null) {
                        RetrofitClient.setToken(token)
                    }

                    // Move to MainActivity if there is a login response
                    if (loginResponse != null) {
                        Toast.makeText(this@LoginActivity, loginResponse.message, Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(intent)
                    }
                } else {
                    val errorResponse = response.errorBody()?.string()
                    Toast.makeText(this@LoginActivity, "Login failed: $errorResponse", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Toast.makeText(this@LoginActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun signUpClick() {
        val intent = Intent(this, SignupActivity::class.java)
        startActivity(intent)
    }
}

