package com.example.fokus.fragments


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.example.fokus.R
import com.example.fokus.activities.MainActivity
import com.example.fokus.activities.SecondActivity
import com.example.fokus.api.APIService
import com.example.fokus.api.RetrofitClient
import com.example.fokus.api.clearToken
import com.example.fokus.api.saveUser
import com.example.fokus.models.ProfilePictureResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var apiService: APIService
    private lateinit var logoutBtn: LinearLayout
    private lateinit var profilePicture: ImageView
    private lateinit var tvUsername: TextView
    private lateinit var tvEmail: TextView
    private lateinit var tvProfile: TextView
    private val save = saveUser()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]

        logoutBtn = view.findViewById(R.id.logoutBtn)
        profilePicture = view.findViewById(R.id.profilePicture)
        tvUsername = view.findViewById(R.id.tvUsername)
        tvEmail = view.findViewById(R.id.tvEmail)
        tvProfile = view.findViewById(R.id.tvProfile)
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout)
        apiService = RetrofitClient.create(APIService::class.java)

        refreshInfo()

        logoutBtn.setOnClickListener {
            (requireActivity() as MainActivity).stopMusic()
            clearToken(requireContext().applicationContext)
            val intent = Intent(requireContext(), SecondActivity::class.java)
            startActivity(intent)
        }

        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.postDelayed({
                refreshInfo()
                swipeRefreshLayout.isRefreshing = false
            }, 1000)
        }

        viewModel.textColor.observe(viewLifecycleOwner, Observer { color ->
            tvProfile.setTextColor(color)
            tvUsername.setTextColor(color)
            tvEmail.setTextColor(color)
        })
    }

    private fun refreshInfo() {
        save.getId(requireContext().applicationContext)?.let { getImage(it) }
        val username = save.getUsername(requireContext().applicationContext)
        val email = save.getEmail(requireContext().applicationContext)
        tvUsername.text = username
        tvEmail.text = email
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