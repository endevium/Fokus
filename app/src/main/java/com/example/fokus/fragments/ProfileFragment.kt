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
import com.example.fokus.R
import com.example.fokus.activities.SecondActivity
import com.example.fokus.api.clearToken
import com.example.fokus.api.saveUser

class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
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

        refreshInfo()

        logoutBtn.setOnClickListener {
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
        val username = save.getUsername(requireContext().applicationContext)
        val email = save.getEmail(requireContext().applicationContext)
        tvUsername.text = username
        tvEmail.text = email
        Log.d("ProfileFragment", "Username: $username, Email: $email")
    }
}