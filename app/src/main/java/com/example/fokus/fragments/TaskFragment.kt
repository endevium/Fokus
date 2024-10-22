package com.example.fokus.fragments

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.text.InputType
import android.view.*
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.fokus.*
import com.example.fokus.api.*
import com.example.fokus.models.*
import retrofit2.*

class TaskFragment : Fragment() {
    private lateinit var tvTask: TextView
    private lateinit var tvTaskDesc: TextView
    private lateinit var taskCardContainer: LinearLayout
    private lateinit var addTaskBtn: ImageButton
    private lateinit var apiService: APIService
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_task, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]

        taskCardContainer = view.findViewById(R.id.taskCardContainer)
        addTaskBtn = view.findViewById(R.id.addTask)
        apiService = RetrofitClient.create(APIService::class.java)
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout)
        tvTask = view.findViewById(R.id.tvTasks)
        tvTaskDesc = view.findViewById(R.id.tvTasksDesc)

        fetchTasks()

        // Create a new task card if clicked
        addTaskBtn.setOnClickListener {
            createTask("Task Title")
        }

        swipeRefreshLayout.setOnRefreshListener {
            refreshTasks()
        }

        viewModel.textColor.observe(viewLifecycleOwner, Observer { color ->
            tvTask.setTextColor(color)
            tvTaskDesc.setTextColor(color)
        })

    }

    private fun fetchTasks() {
        // Send a request to fetch tasks from the database
        apiService.getTasks().enqueue(object: Callback<List<Task>> {
            override fun onResponse(call: Call<List<Task>>, response: Response<List<Task>>) {
                if (response.isSuccessful) {
                    val taskList  = response.body()!!
                    for (task in taskList) {
                        val taskTitle = task.task_title
                        createTaskCard(taskCardContainer, taskTitle, task.id)
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

    private fun refreshTasks() {
        swipeRefreshLayout.postDelayed({
            for (i in taskCardContainer.childCount - 1 downTo 0) {
                val view = taskCardContainer.getChildAt(i)
                if (view is CardView) {
                    taskCardContainer.removeViewAt(i)
                }
            }

            fetchTasks()

            swipeRefreshLayout.isRefreshing = false
        }, 1000)
    }

    private fun updateTask(id: Int, taskTitle: String) {
        apiService.updateTask(id, taskTitle).enqueue(object: Callback<TaskResponse> {
            override fun onResponse(call: Call<TaskResponse>, response: Response<TaskResponse>) {
                if (!response.isSuccessful) {
                    val errorResponse = response.errorBody()?.string()
                    Toast.makeText(requireContext(), "Error updating task: $errorResponse", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<TaskResponse>, t: Throwable) {
                Toast.makeText(requireContext(), "Fatal error: ${t.message}", Toast.LENGTH_LONG).show()
            }

        })
    }

    private fun createTask(taskTitle: String) {
        apiService.createTask(taskTitle).enqueue(object: Callback<TaskResponse> {
            override fun onResponse(call: Call<TaskResponse>, response: Response<TaskResponse>) {
                if (response.isSuccessful) {
                    val createTaskResponse = response.body()!!

                    if (createTaskResponse != null) {
                        for (i in taskCardContainer.childCount - 1 downTo 0) {
                            val view = taskCardContainer.getChildAt(i)
                            if (view is CardView) {
                                taskCardContainer.removeViewAt(i)
                            }
                        }

                        fetchTasks()
                        Toast.makeText(requireContext(), "New task created", Toast.LENGTH_LONG).show()
                    }
                } else {
                    val errorResponse = response.errorBody()?.string()
                    Toast.makeText(requireContext(), "Error creating new task: $errorResponse", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<TaskResponse>, t: Throwable) {
                Toast.makeText(requireContext(), "Fatal error: ${t.message}", Toast.LENGTH_LONG).show()
            }

        })
    }

    private fun deleteTask(id: Int) {
        apiService.deleteTask(id).enqueue(object : Callback<TaskDeleteResponse> {
            override fun onResponse(call: Call<TaskDeleteResponse>, response: Response<TaskDeleteResponse>) {
                if (response.isSuccessful) {
                    Toast.makeText(requireContext(), "Successfully deleted task", Toast.LENGTH_LONG).show()
                } else {
                    val errorResponse = response.errorBody()?.string() ?: "Unknown error"
                    Toast.makeText(requireContext(), "Failed to delete task: $errorResponse", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<TaskDeleteResponse>, t: Throwable) {
                Toast.makeText(requireContext(), "Deleting task failed: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }


    // FOR CREATING A NEW NON-EMPTY TASK CARD
    private fun createTaskCard(parentLayout: LinearLayout, taskTitle: String, id: Int) {
        val taskCardView = CardView(requireContext()).apply {
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
                setMargins(-5, 0, 10, 0)
            }
        }

        // Task input layout
        val inputField = EditText(requireContext()).apply {
            layoutParams = LinearLayout.LayoutParams(
                600,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            // Change visual layout
            background = null

            if (taskTitle != "Task Title") {
                setText(taskTitle)
            } else {
                setText("")
                setHint("Task Name")
            }

            setTextColor(resources.getColor(R.color.lightblack, null))
            setHintTextColor(resources.getColor(R.color.lightgray, null))

            imeOptions = EditorInfo.IME_ACTION_DONE
            inputType = InputType.TYPE_CLASS_TEXT
        }

        checkbox.setOnCheckedChangeListener{_, isChecked ->
            if (isChecked) {
                inputField.paintFlags = inputField.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                inputField.paintFlags = inputField.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }
        }

        inputField.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE ||
                (event != null && event.keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN)) {
                val imm = v.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(v.windowToken, 0)
                inputField.clearFocus()

                updateTask(id, inputField.text.toString())

                true
            } else {
                false
            }
        }

        val deleteBtn = ImageButton(requireContext()).apply {
            layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            setBackgroundColor(Color.TRANSPARENT)
            setImageResource(R.drawable.close)
        }

        deleteBtn.setOnClickListener {
            deleteTask(id)
            parentLayout.removeView(taskCardView)
        }

        linearLayout.addView(checkbox)
        linearLayout.addView(inputField)
        linearLayout.addView(deleteBtn)
        taskCardView.addView(linearLayout) // Wrap all elements
        parentLayout.addView(taskCardView, 0) // Display task at the top of the add task button
    }
}
