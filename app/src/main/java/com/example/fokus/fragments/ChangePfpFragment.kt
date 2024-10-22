package com.example.fokus

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.fokus.api.APIService
import com.example.fokus.api.FileUtil
import com.example.fokus.api.RetrofitClient
import com.example.fokus.api.saveUser
import com.example.fokus.models.ProfilePictureResponse
import com.github.dhaval2404.imagepicker.ImagePicker
import okhttp3.MultipartBody
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.InputStream
import retrofit2.*
import java.io.File

class ChangePfpFragment : Fragment(R.layout.fragment_changepfp) {
    private lateinit var backBtn: ImageButton
    private lateinit var profilePicture: ImageView
    private lateinit var username: TextView
    private lateinit var editProfile: TextView
    private lateinit var cancelBtn: TextView
    private lateinit var saveBtn: TextView
    private lateinit var apiService: APIService
    private val save = saveUser()
    private var fileUri: Uri? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        backBtn = view.findViewById(R.id.backBtn)
        profilePicture = view.findViewById(R.id.profilePicture)
        username = view.findViewById(R.id.username)
        editProfile = view.findViewById(R.id.editProfilePicture)
        cancelBtn = view.findViewById(R.id.cancelBtn)
        saveBtn = view.findViewById(R.id.saveBtn)
        apiService = RetrofitClient.create(APIService::class.java)

        username.text = save.getUsername(requireContext().applicationContext)
        save.getId(requireContext().applicationContext)?.let { getImage(it) }

        backBtn.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        cancelBtn.setOnClickListener {
            parentFragmentManager.popBackStack()
        }



        editProfile.setOnClickListener {
            ImagePicker.with(this)
                .cropSquare()
                .compress(4096)
                .maxResultSize(512, 512)
                .start()
        }

        saveBtn.setOnClickListener {
            uploadImage(fileUri)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            val uri: Uri? = data?.data
            uri?.let {
                fileUri = it

                Glide.with(requireContext())
                    .load(fileUri)
                    .circleCrop()
                    .into(profilePicture)
            }
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(requireContext(), ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        }
    }

    private fun uploadImage(imageUri: Uri?) {
        // Convert URI to file
        val file = File(imageUri?.path)

        // Create RequestBody and MultipartBody.Part for file
        val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
        val body = MultipartBody.Part.createFormData("profile_picture", file.name, requestFile)

        // Call the API using Retrofit
        apiService.uploadProfile(body)
            .enqueue(object : Callback<ProfilePictureResponse> {
                override fun onResponse(call: Call<ProfilePictureResponse>, response: Response<ProfilePictureResponse>) {
                    if (response.isSuccessful) {
                        Toast.makeText(requireContext(), "Upload successful!", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(requireContext(), "Upload failed: ${response.message()}", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ProfilePictureResponse>, t: Throwable) {
                    Toast.makeText(requireContext(), "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun getImage(id: Int) {
        apiService.getProfile(id).enqueue(object: Callback<ProfilePictureResponse> {
            override fun onResponse(call: Call<ProfilePictureResponse>, response: Response<ProfilePictureResponse>) {
                if (response.isSuccessful) {
                    val profilePictureUrl = response.body()?.profile_picture_url
                    profilePictureUrl?.let {
                        // Load the image into the ImageView using Glide
                        Glide.with(requireContext())
                            .load(it)
                            .circleCrop()
                            .into(profilePicture)
                    }
                } else {
                    Toast.makeText(requireContext(), "Failed to load profile picture", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ProfilePictureResponse>, t: Throwable) {
                Toast.makeText(requireContext(), "Failed to load profile picture", Toast.LENGTH_SHORT).show()
            }

        })
    }
}
