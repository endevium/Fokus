package com.example.fokus


import android.os.Bundle
import android.text.InputType
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.fokus.api.*
import com.example.fokus.models.UserResponse
import retrofit2.*


class ChangePasswordFragment : Fragment(R.layout.fragment_changepassword) {
    private lateinit var oldPassword: EditText
    private lateinit var newPassword: EditText
    private lateinit var backBtn: ImageButton
    private lateinit var cancelBtn: TextView
    private lateinit var saveBtn: TextView
    private lateinit var showBtn: ImageButton
    private lateinit var hideBtn: ImageButton
    private lateinit var showBtn2: ImageButton
    private lateinit var hideBtn2: ImageButton
    private lateinit var apiService: APIService
    private val save = saveUser()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        oldPassword = view.findViewById(R.id.oldPassword)
        newPassword = view.findViewById(R.id.newPassword)
        backBtn = view.findViewById(R.id.backBtn)
        cancelBtn = view.findViewById(R.id.cancelBtn)
        saveBtn = view.findViewById(R.id.saveBtn)
        showBtn = view.findViewById(R.id.showBtn)
        hideBtn = view.findViewById(R.id.hideBtn)
        showBtn2 = view.findViewById(R.id.showBtn2)
        hideBtn2 = view.findViewById(R.id.hideBtn2)
        apiService = RetrofitClient.create(APIService::class.java)


        //Show and Hide button transaction


        // Toggle visibility for old password
        showBtn.setOnClickListener {
            togglePasswordVisibility(oldPassword, showBtn, hideBtn, true)
        }
        hideBtn.setOnClickListener {
            togglePasswordVisibility(oldPassword, showBtn, hideBtn, false)
        }


        // Toggle visibility for new password
        showBtn2.setOnClickListener {
            togglePasswordVisibility(newPassword, showBtn2, hideBtn2, true)
        }
        hideBtn2.setOnClickListener {
            togglePasswordVisibility(newPassword, showBtn2, hideBtn2, false)
        }


        cancelBtn.setOnClickListener {
            parentFragmentManager.popBackStack()
        }


        backBtn.setOnClickListener {
            parentFragmentManager.popBackStack()
        }


        saveBtn.setOnClickListener {
            val id = save.getId(requireContext().applicationContext)
            val oldPasswordText = oldPassword.text.toString()
            val newPasswordText = newPassword.text.toString()


            if (oldPasswordText.isNotEmpty() && newPasswordText.isNotEmpty()) {
                if (oldPasswordText.length >= 8 && newPasswordText.length >= 8) {
                    if (oldPasswordText == save.getPassword(requireContext().applicationContext)) {
                        if (id != null && newPasswordText != save.getPassword(requireContext().applicationContext)) {
                            changePassword(id, newPasswordText)
                        }
                    } else {
                        Toast.makeText(requireContext(), "Password does not match", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(requireContext(), "Your password must be at least 8 characters", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), "Please enter your old password and a new password", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun togglePasswordVisibility(editText: EditText, showBtn: ImageButton, hideBtn: ImageButton, show: Boolean) {
        val typeface = editText.typeface
        val selection = editText.selectionStart


        if (show) {
            // Show password
            editText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            editText.transformationMethod = null
            showBtn.visibility = View.GONE
            hideBtn.visibility = View.VISIBLE
        } else {
            // Hide password
            editText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            editText.transformationMethod = PasswordTransformationMethod.getInstance()
            showBtn.visibility = View.VISIBLE
            hideBtn.visibility = View.GONE
        }


        // Restore the cursor position and typeface
        editText.typeface = typeface
        editText.setSelection(selection)
    }


    private fun changePassword(id: Int, password: String) {
        apiService.changePassword(id, password).enqueue(object: Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {
                    save.savePass(requireContext().applicationContext, password)
                    Toast.makeText(requireContext(), "Changed password successfully", Toast.LENGTH_LONG).show()
                } else {
                    val errorResponse = response.errorBody()?.string()
                    Toast.makeText(requireContext(), "$errorResponse", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Toast.makeText(requireContext(), "${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }
}



