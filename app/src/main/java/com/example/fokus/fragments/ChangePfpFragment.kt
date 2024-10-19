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

    private fun uploadImage(url: Uri?) {
        //
    }
}
