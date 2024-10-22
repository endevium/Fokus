package com.example.fokus.activities


import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.text.method.PasswordTransformationMethod
import android.util.Patterns
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.fokus.R
import com.example.fokus.api.APIService
import com.example.fokus.api.RetrofitClient
import com.example.fokus.api.saveUser
import com.example.fokus.models.ChangePasswordResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ForgotPasswordActivity : AppCompatActivity() {
    private lateinit var sUser: EditText
    private lateinit var sPass: EditText
    private lateinit var sConfirmPass: EditText
    private lateinit var showPassBtn: ImageButton
    private lateinit var hidePassBtn: ImageButton
    private lateinit var backBtn: ImageButton
    private lateinit var showConfirmPassBtn: ImageButton
    private lateinit var hideConfirmPassBtn: ImageButton
    private lateinit var resetBtn: Button
    private lateinit var apiService: APIService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgotpassword)

        backBtn = findViewById(R.id.backBtn)
        sUser = findViewById(R.id.sUser)
        sPass = findViewById(R.id.sPass)
        sConfirmPass = findViewById(R.id.sConfirmPass)
        showPassBtn = findViewById(R.id.showBtn)
        hidePassBtn = findViewById(R.id.hideBtn)
        resetBtn = findViewById(R.id.resetBtn)
        showConfirmPassBtn = findViewById(R.id.showConfirmBtn)
        hideConfirmPassBtn = findViewById(R.id.hideConfirmBtn)
        apiService = RetrofitClient.create(APIService::class.java)

        backBtn.setOnClickListener {
            val Intent = Intent(this, LoginActivity::class.java)
            startActivity(Intent)
        }

        //Show and Hide button transaction
        // Show password when show button is clicked (for password field)
        showPassBtn.setOnClickListener {
            togglePasswordVisibility(sPass, showPassBtn, hidePassBtn, true)
        }
        // Hide password when hide button is clicked (for password field)
        hidePassBtn.setOnClickListener {
            togglePasswordVisibility(sPass, showPassBtn, hidePassBtn, false)
        }
        // Show confirm password when show button is clicked (for confirm password field)
        showConfirmPassBtn.setOnClickListener {
            togglePasswordVisibility(sConfirmPass, showConfirmPassBtn, hideConfirmPassBtn, true)
        }
        // Hide confirm password when hide button is clicked (for confirm password field)
        hideConfirmPassBtn.setOnClickListener {
            togglePasswordVisibility(sConfirmPass, showConfirmPassBtn, hideConfirmPassBtn, false)
        }

        resetBtn.setOnClickListener {
            resetBtn.isEnabled = false
            resetBtn.text = "Loading..."
            val email = sUser.text.toString()
            val newPass = sPass.text.toString()
            val confirmPass = sConfirmPass.text.toString()

            if (email.isNotEmpty() && newPass.isNotEmpty() && confirmPass.isNotEmpty()) {
                if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    if (newPass.length >= 8) {
                        if (confirmPass == newPass) {
                            resetPassword(email, newPass)
                        } else {
                            Toast.makeText(applicationContext, "Confirm password does not match", Toast.LENGTH_LONG).show()
                            resetBtn.isEnabled = true
                            resetBtn.text = "Reset Password"
                        }
                    } else {
                        Toast.makeText(applicationContext, "Password must be at least 8 characters length", Toast.LENGTH_LONG).show()
                        resetBtn.isEnabled = true
                        resetBtn.text = "Reset Password"
                    }
                } else {
                    Toast.makeText(applicationContext, "Invalid e-mail address", Toast.LENGTH_LONG).show()
                    resetBtn.isEnabled = true
                    resetBtn.text = "Reset Password"
                }
            } else {
                Toast.makeText(applicationContext, "Please enter your e-mail address and a new password", Toast.LENGTH_LONG).show()
                resetBtn.isEnabled = true
                resetBtn.text = "Reset Password"
            }
        }
    }

    private fun resetPassword(email: String, password: String) {
        apiService.resetPassword(email, password).enqueue(object: Callback<ChangePasswordResponse> {
            override fun onResponse(call: Call<ChangePasswordResponse>, response: Response<ChangePasswordResponse>) {
                if (response.isSuccessful) {
                    Toast.makeText(applicationContext, "Password reset successfully", Toast.LENGTH_LONG).show()
                } else {
                    val errorResponse = response.errorBody()?.string()
                    Toast.makeText(applicationContext, "Error: $errorResponse", Toast.LENGTH_LONG).show()
                    resetBtn.isEnabled = true
                    resetBtn.text = "Reset Password"
                }
            }

            override fun onFailure(call: Call<ChangePasswordResponse>, t: Throwable) {
                Toast.makeText(applicationContext, "${t.message}", Toast.LENGTH_LONG).show()
                resetBtn.isEnabled = true
                resetBtn.text = "Reset Password"
            }

        })
    }


    // Function to toggle the password visibility for any EditText
    private fun togglePasswordVisibility(
        editText: EditText,
        showBtn: ImageButton,
        hideBtn: ImageButton,
        show: Boolean
    ) {
        // Store the current typeface and cursor position
        val typeface = editText.typeface
        val selection = editText.selectionStart


        if (show) {
            // Show password
            editText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            editText.transformationMethod = null


            // Toggle button visibility
            showBtn.visibility = View.GONE
            hideBtn.visibility = View.VISIBLE
        } else {
            // Hide password
            editText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            editText.transformationMethod = PasswordTransformationMethod.getInstance()


            // Toggle button visibility
            showBtn.visibility = View.VISIBLE
            hideBtn.visibility = View.GONE
        }


        // Restore the cursor position and typeface
        editText.typeface = typeface
        editText.setSelection(selection)
    }
}

