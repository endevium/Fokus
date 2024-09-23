package com.example.fokus

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import retrofit2.*

class SignupActivity : AppCompatActivity() {
    private lateinit var sUser: EditText
    private lateinit var sEmail: EditText
    private lateinit var sPass: EditText
    private lateinit var signUpBtn: Button
    private lateinit var loginBtnT: TextView

    private lateinit var apiService: APIService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_signup)

        // Variables
        apiService = RetrofitClient.create(APIService::class.java)
        sUser = findViewById(R.id.sUser)
        sEmail = findViewById(R.id.sEmail)
        sPass = findViewById(R.id.sPass)
        signUpBtn = findViewById(R.id.sBtn)
        loginBtnT = findViewById(R.id.loginD)

        // OnClickListeners
        signUpBtn.setOnClickListener {
            val username = sUser.text.toString().trim()
            val fullname = sUser.text.toString().trim() // FULLNAME TO BE REMOVED
            val email = sEmail.text.toString().trim()
            val password = sPass.text.toString().trim()

            if (validateUser() && validateEmail() && validatePass()) {
                createAccount(username, fullname, password, email)
            }
        }

        loginBtnT.setOnClickListener{
            logInClick()
        }

    }

    private fun validateUser(): Boolean {
        val input = sUser.text.toString()

        if (input.isEmpty()) {
            Toast.makeText(applicationContext, "Please enter a username", Toast.LENGTH_LONG).show()
            return false
        }

        return true
    }

    private fun validateEmail(): Boolean {
        val input = sEmail.text.toString()

        if (input.isEmpty()) {
            Toast.makeText(applicationContext, "Please enter an e-mail address", Toast.LENGTH_LONG).show()
            return false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(input).matches()) {
            Toast.makeText(applicationContext, "Invalid e-mail address", Toast.LENGTH_LONG).show()
            return false
        }

        return true
    }

    private fun validatePass(): Boolean {
        val input = sPass.text.toString()

        if (input.isEmpty()) {
            Toast.makeText(applicationContext, "Please enter a password", Toast.LENGTH_LONG).show()
            return false
        } else if (input.length < 8) {
            Toast.makeText(applicationContext, "Your password must be at least 8 characters", Toast.LENGTH_LONG).show()
            return false
        }

        return true
    }

    private fun createAccount(fullname: String, username: String, password: String, email: String) {
        apiService.signup(username, fullname, password, email).enqueue(object : Callback<SignupResponse> {
            override fun onResponse(call: Call<SignupResponse>, response: Response<SignupResponse>) {
                if (response.isSuccessful) {
                    val signupResponse = response.body()
                    if (signupResponse != null) {
                        Toast.makeText(this@SignupActivity, signupResponse.message, Toast.LENGTH_SHORT).show()
                        // Handle successful login
                    }
                } else {
                    val errorResponse = response.errorBody()?.string()
                    Log.e("SignupError", "Error: $errorResponse")
                    Toast.makeText(this@SignupActivity, "Signup Failed: $errorResponse", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<SignupResponse>, t: Throwable) {
                Toast.makeText(this@SignupActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun logInClick() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}