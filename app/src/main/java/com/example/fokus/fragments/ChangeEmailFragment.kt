package com.example.fokus

import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.fokus.api.*
import com.example.fokus.models.UserResponse
import retrofit2.*

class ChangeEmailFragment : Fragment(R.layout.fragment_changeemail) {
    private lateinit var oldEmail: EditText
    private lateinit var newEmail: EditText
    private lateinit var backBtn: ImageButton
    private lateinit var cancelBtn: TextView
    private lateinit var saveBtn: TextView
    private lateinit var apiService: APIService
    private val save = saveUser()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        oldEmail = view.findViewById(R.id.oldEmail)
        newEmail = view.findViewById(R.id.newEmail)
        backBtn = view.findViewById(R.id.backBtn)
        cancelBtn = view.findViewById(R.id.cancelBtn)
        saveBtn = view.findViewById(R.id.saveBtn)
        apiService = RetrofitClient.create(APIService::class.java)

        cancelBtn.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        backBtn.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        saveBtn.setOnClickListener {
            val id = save.getId(requireContext().applicationContext)
            val oldEmailText = oldEmail.text.toString()
            val newEmailText = newEmail.text.toString()

            if (oldEmailText.isNotEmpty() && newEmailText.isNotEmpty()) {
                if (Patterns.EMAIL_ADDRESS.matcher(oldEmailText).matches() && Patterns.EMAIL_ADDRESS.matcher(newEmailText).matches()) {
                    if (oldEmailText == save.getEmail(requireContext().applicationContext)) {
                        if (id != null) {
                            changeEmail(id, newEmailText)
                        }
                    } else {
                        Toast.makeText(requireContext(), "E-mail does not match", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(requireContext(), "Invalid e-mail address", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), "Please enter your old e-mail address and a new e-mail address", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun changeEmail(id: Int, email: String) {
        apiService.changeEmail(id, email).enqueue(object: Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {
                    save.saveEmail(requireContext().applicationContext, email)
                    Toast.makeText(requireContext(), "Changed e-mail successfully", Toast.LENGTH_LONG).show()
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