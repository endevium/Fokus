package com.example.fokus.fragments

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.fokus.EditNotesFragment
import com.example.fokus.R
import com.example.fokus.api.APIService
import com.example.fokus.api.RetrofitClient
import com.example.fokus.models.TaskHistory
import com.example.fokus.models.TaskHistoryResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TaskHistoryFragment: Fragment(R.layout.fragment_task_history) {
    private lateinit var taskHistoryContainer: LinearLayout
    private lateinit var tvTaskHistory: TextView
    private lateinit var tvTaskHistoryDesc: TextView
    private lateinit var backBtn: ImageButton
    private lateinit var apiService: APIService
    private val taskHistoryList: MutableList<TaskHistory> = mutableListOf()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]
        tvTaskHistory = view.findViewById(R.id.tvTaskHistory)
        tvTaskHistoryDesc = view.findViewById(R.id.tvTaskHistoryDesc)
        taskHistoryContainer = view.findViewById(R.id.taskHistoryContainer)
        backBtn = view.findViewById(R.id.backBtn)
        apiService = RetrofitClient.create(APIService::class.java)

        backBtn.setOnClickListener {
            parentFragmentManager.setFragmentResult("exitButtonClicked", Bundle())
            requireActivity().supportFragmentManager.popBackStack()
        }

        viewModel.textColor.observe(viewLifecycleOwner, Observer { color ->
            tvTaskHistory.setTextColor(color)
            tvTaskHistoryDesc.setTextColor(color)
        })

        viewModel.backColor.observe(viewLifecycleOwner, Observer { drawable ->
            backBtn.setImageResource(drawable)
        })

        fetchTaskHistory()
    }

    private fun fetchTaskHistory() {
        apiService.getTaskHistory().enqueue(object: Callback<TaskHistoryResponse> {
            override fun onResponse(call: Call<TaskHistoryResponse>, response: Response<TaskHistoryResponse>) {
                if (response.isSuccessful) {
                    taskHistoryList.clear()
                    taskHistoryList.addAll(response.body()!!.task_history)
                    displayTasks()
                } else {
                    Toast.makeText(requireContext(), "Fetching task history failed", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<TaskHistoryResponse>, t: Throwable) {
                Log.e("Error", "${t.message}")
                Toast.makeText(requireContext(), "An internet error occured ${t.message}", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun displayTasks() {
        taskHistoryContainer.removeAllViews()
        for (task in taskHistoryList) {
            createTaskCard(taskHistoryContainer, task.task_id, task.status, task.description)
        }
    }

    private fun createTaskCard(parentLayout: LinearLayout, id: Int, status: String, content: String) {
        // Task card layout
        val taskCardView = CardView(requireContext()).apply {
            layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(0, 0, 0, 40)
            }

            // Change corner radius
            radius = 30f
            cardElevation = 16f

            // Change the background of the card
            setCardBackgroundColor(resources.getColor(R.color.white, null))
        }

        // Linear layout holder for status and description
        val firstLinearLayout = LinearLayout(requireContext()).apply {
            layoutParams = LinearLayout.LayoutParams(
                850,
                300
            )
            background = ContextCompat.getDrawable(requireContext(), R.drawable.ncontainer)
            orientation = LinearLayout.VERTICAL
        }

        val taskId = TextView(requireContext()).apply {
            layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            ).apply {
                marginStart = 30
                setMargins(0, 0, 0, 0)
            }

            // Change visual layout
            background = null
            text = "Task ID: $id"
        }

        // Task status layout
        val taskStatus = TextView(requireContext()).apply {
            layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            ).apply {
                // Move element to the left or the start
                setGravity(Gravity.START)
                marginStart = 30
                setMargins(0, 30, 0, 0)
            }

            // Change visual layout
            background = null
            text = status
            setTextColor(resources.getColor(R.color.lightblack, null))
            textSize = 16f
            setTypeface(null, Typeface.BOLD)
        }

        val secondLinearLayout = LinearLayout(requireContext()).apply {
            layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                120
            ).apply {
                setMargins(0, 0, 0, 0)
            }

            orientation = LinearLayout.VERTICAL

        }

        // Task description layout
        val taskContent = TextView(requireContext()).apply {
            layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            ).apply {
                marginStart = 30
                setMargins(0, 0, 0, 0)
            }

            // Change visual layout
            background = null
            text = content
        }

        firstLinearLayout.addView(taskStatus) // Add note title
        firstLinearLayout.addView(secondLinearLayout) // Add second linear layout for note description
        secondLinearLayout.addView(taskId)
        secondLinearLayout.addView(taskContent) // Add note description inside second linear layout
        taskCardView.addView(firstLinearLayout) // Wrap all elements in the card
        parentLayout.addView(taskCardView) // Display the card
    }
}