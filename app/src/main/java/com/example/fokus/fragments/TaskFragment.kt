package com.example.fokus.fragments

import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.fokus.*
import com.example.fokus.api.*
import com.example.fokus.models.*
import retrofit2.*

class TaskFragment : Fragment() {
    private lateinit var taskCardContainer: LinearLayout
    private lateinit var addTaskBtn: ImageButton
    private lateinit var apiService: APIService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_task, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        taskCardContainer = view.findViewById(R.id.taskCardContainer)
        addTaskBtn = view.findViewById(R.id.addTask)
        apiService = RetrofitClient.create(APIService::class.java)

        fetchTasks()

        // Create a new task card if clicked
        addTaskBtn.setOnClickListener {
            createEmptyTaskCard(taskCardContainer)
        }
    }

    private fun fetchTasks() {
    // Send a request to fetch tasks from the database
        apiService.getTasks().enqueue(object: Callback<List<Task>> {
            override fun onResponse(call: Call<List<Task>>, response: Response<List<Task>>) {
                if (response.isSuccessful) {
                    val taskList  = response.body()!!
                    for (task in taskList) {
                        val taskTitle = task.task_title
                        createTaskCard(taskCardContainer, taskTitle)
                    }
                } else {
                    Toast.makeText(requireContext(), "Fetching tasks failed", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Task>>, t: Throwable) {
                Toast.makeText(requireContext(), "Fetching tasks failed: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // FOR CREATING A NEW NON-EMPTY TASK CARD
    private fun createTaskCard(parentLayout: LinearLayout, taskTitle: String) {
        val taskCardView = CardView(requireContext()).apply {
            id = View.generateViewId()
            layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(0, 0, 0, 40)
            }
            // Corner radius
            radius = 24f
            cardElevation = 18f

            // Change card background color
            setCardBackgroundColor(resources.getColor(R.color.white, null))
        }

        // Linear layout for the checkbox and input field
        val linearLayout = LinearLayout(requireContext()).apply {
            layoutParams = LinearLayout.LayoutParams(
                850,
                180
            )

            // Change visual layout
            background = ContextCompat.getDrawable(requireContext(), R.drawable.etcontainer)
            gravity = Gravity.CENTER // Center position
            orientation = LinearLayout.HORIZONTAL
        }

        // Checkbox layout
        val checkbox = CheckBox(requireContext()).apply {
            layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
            ).apply {
                setMargins(22, 0, 16, 0)
            }
        }

        // Task input layout
        val inputField = EditText(requireContext()).apply {
            id = View.generateViewId()
            layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            // Change visual layout
            background = null
            setText(taskTitle)
            setTextColor(resources.getColor(R.color.lightblack, null))
            setHintTextColor(resources.getColor(R.color.lightgray, null))
        }

        linearLayout.addView(checkbox)
        linearLayout.addView(inputField)
        taskCardView.addView(linearLayout) // Wrap all elements
        parentLayout.addView(taskCardView, 0) // Display task at the top of the add task button
    }

    // FOR CREATING AN EMPTY TASK
    private fun createEmptyTaskCard(parentLayout: LinearLayout) {
        val taskCardView = CardView(requireContext()).apply {
            id = View.generateViewId()
            layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(0, 0, 0, 40)
            }
            // Corner radius
            radius = 24f
            cardElevation = 18f

            // Change card background color
            setCardBackgroundColor(resources.getColor(R.color.white, null))
        }

        // Linear layout for the checkbox and input field
        val linearLayout = LinearLayout(requireContext()).apply {
            layoutParams = LinearLayout.LayoutParams(
                850,
                180
            )

            // Change visual layout
            background = ContextCompat.getDrawable(requireContext(), R.drawable.etcontainer)
            gravity = Gravity.CENTER // Center position
            orientation = LinearLayout.HORIZONTAL
        }

        // Checkbox layout
        val checkbox = CheckBox(requireContext()).apply {
            layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
            ).apply {
                setMargins(22, 0, 16, 0)
            }
        }

        // Task input layout
        val inputField = EditText(requireContext()).apply {
            id = View.generateViewId()
            layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            // Change visual layout
            background = null
            hint = "Task Name"
            setTextColor(resources.getColor(R.color.lightblack, null))
            setHintTextColor(resources.getColor(R.color.lightgray, null))
        }

        linearLayout.addView(checkbox)
        linearLayout.addView(inputField)
        taskCardView.addView(linearLayout) // Wrap all elements
        parentLayout.addView(taskCardView, 0) // Display task at the top of the add task button
    }
}
