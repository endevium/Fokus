package com.example.fokus

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import retrofit2.*

class LoginActivity : AppCompatActivity() {
    private lateinit var lEmail: EditText
    private lateinit var lPass: EditText
    private lateinit var loginBtn: Button
    private lateinit var signUp: TextView

    private lateinit var apiService: APIService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        enableEdgeToEdge()

        // Variables
        apiService = RetrofitClient.create(APIService::class.java)
        lEmail = findViewById(R.id.lEmail)
        lPass = findViewById(R.id.lPass)
        loginBtn = findViewById(R.id.lBtn)
        signUp = findViewById(R.id.signupD)

        // OnClickListeners
        loginBtn.setOnClickListener {
            val email = lEmail.text.toString().trim()
            val password = lPass.text.toString().trim()

            if (validateEmail() && validatePass()) {
                login(email, password)
            }
        }

        signUp.setOnClickListener {
            signUpClick()
        }
    }

    private fun validateEmail(): Boolean {
        val input = lEmail.text.toString()

        if (input.isEmpty()) {
            Toast.makeText(applicationContext, "Please enter your e-mail address", Toast.LENGTH_LONG).show()
            return false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(input).matches()) {
            Toast.makeText(applicationContext, "Invalid e-mail address", Toast.LENGTH_LONG).show()
            return false
        }

        return true
    }

    private fun validatePass(): Boolean {
        val input = lPass.text.toString()

        if (input.isEmpty()) {
            Toast.makeText(applicationContext, "Please enter your password", Toast.LENGTH_LONG).show()
            return false
        }

        return true
    }

    private fun login(email: String, password: String) {
        apiService.login(email, password).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val loginResponse = response.body()
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

