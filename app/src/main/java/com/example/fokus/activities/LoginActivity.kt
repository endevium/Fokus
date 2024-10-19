package com.example.fokus.activities

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.fokus.api.APIService
import com.example.fokus.models.LoginResponse
import com.example.fokus.api.RetrofitClient
import com.example.fokus.R
import com.example.fokus.api.*
import retrofit2.*

class LoginActivity : AppCompatActivity() {
    private lateinit var lEmail: EditText
    private lateinit var lPass: EditText
    private lateinit var loginBtn: Button
    private lateinit var signUp: TextView
    private lateinit var rememberMe: CheckBox
    private lateinit var showPassBtn: ImageButton
    private lateinit var hidePassBtn: ImageButton
    private lateinit var apiService: APIService
    private lateinit var forgotPasswordLayout: LinearLayout
    private val save = saveUser()

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
        showPassBtn = findViewById(R.id.showBtn)
        hidePassBtn = findViewById(R.id.hideBtn)
        forgotPasswordLayout = findViewById(R.id.forgotpassBtn)

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

        //Show and Hide button transaction
        showPassBtn.setOnClickListener{
            val currentTypeface = lPass.typeface
            val currentSelection = lPass.selectionStart


            lPass.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            lPass.transformationMethod = null  // Ensure the password is shown in plain text
            lPass.typeface = currentTypeface
            lPass.setSelection(currentSelection) // Keep cursor at the end
            showPassBtn.visibility = View.GONE
            hidePassBtn.visibility = View.VISIBLE
        }


        hidePassBtn.setOnClickListener{
            val currentTypeface = lPass.typeface
            val currentSelection = lPass.selectionStart


            lPass.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            lPass.transformationMethod = PasswordTransformationMethod.getInstance()  // Make sure the password is hidden
            lPass.typeface = currentTypeface
            lPass.setSelection(currentSelection) // Keep cursor at the end
            hidePassBtn.visibility = View.GONE
            showPassBtn.visibility = View.VISIBLE
        }


        forgotPasswordLayout.setOnClickListener {
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)
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
                    val idFromResponse = loginResponse?.data?.id
                    val usernameFromResponse = loginResponse?.data?.username
                    val emailFromResponse = loginResponse?.data?.email

                    // Save token if not null
                    if (token != null && idFromResponse != null && usernameFromResponse != null
                        && emailFromResponse != null) {
                        saveToken(applicationContext, token)
                        RetrofitClient.setToken(token)
                        save.saveId(applicationContext, idFromResponse)
                        save.saveUsername(applicationContext, usernameFromResponse)
                        save.saveEmail(applicationContext, emailFromResponse)
                        save.savePass(applicationContext, password)

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

