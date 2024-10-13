package com.example.fokus

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.fokus.api.*
import com.example.fokus.models.UserResponse
import retrofit2.*

class ChangeUsernameFragment : Fragment(R.layout.fragment_changeusername) {
    private lateinit var backBtn: ImageButton
    private lateinit var etUsername: EditText
    private lateinit var cancelBtn: TextView
    private lateinit var saveBtn: TextView
    private lateinit var apiService: APIService
    private val save = saveUser()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        backBtn = view.findViewById(R.id.backBtn)
        etUsername = view.findViewById(R.id.etUsername)
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
            val username = etUsername.text.toString()

            if (username.isNotEmpty()) {
                if (id != null) {
                    changeUsername(id, username)
                }
            }
        }
    }

    private fun changeUsername(id: Int, username: String) {
        apiService.changeUser(id, username).enqueue(object: Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {
                    Toast.makeText(requireContext(), "Changed username successfully", Toast.LENGTH_LONG).show()
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
