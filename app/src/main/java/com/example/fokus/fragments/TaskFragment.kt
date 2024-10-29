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
import androidx.fragment.app.FragmentTransaction
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
    private lateinit var addTextTv: TextView
    private lateinit var taskCardContainer: LinearLayout
    private lateinit var task_container: RelativeLayout
    private lateinit var addTaskBtn: ImageButton
    private lateinit var addTaskContainer: LinearLayout
    private lateinit var taskHistoryContainer: LinearLayout
    private lateinit var apiService: APIService
    private lateinit var taskHistoryBtn: ImageButton
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
        task_container = view.findViewById(R.id.task_container)
        taskHistoryContainer = view.findViewById(R.id.taskHistoryButton)
        addTaskBtn = view.findViewById(R.id.addTask)
        addTaskContainer = view.findViewById(R.id.addTaskContainer)
        apiService = RetrofitClient.create(APIService::class.java)
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout)
        tvTask = view.findViewById(R.id.tvTasks)
        tvTaskDesc = view.findViewById(R.id.tvTasksDesc)
        addTextTv = view.findViewById(R.id.addTaskTv)
        taskHistoryBtn = view.findViewById(R.id.taskHistory)

        fetchTasks()

        viewModel.textColor.observe(viewLifecycleOwner, Observer { color ->
            tvTask.setTextColor(color)
            tvTaskDesc.setTextColor(color)
        })

        viewModel.addColor.observe(viewLifecycleOwner, Observer { drawable ->
            addTaskBtn.setImageResource(drawable)
        })

        // Create a new task card if clicked
        addTaskBtn.setOnClickListener {
            addTaskBtn.isEnabled = false
            createTask("Task Title")
        }

        parentFragmentManager.setFragmentResultListener("exitButtonClicked", viewLifecycleOwner) { _, _ ->
            swipeRefreshLayout.isRefreshing = true
            swipeRefreshLayout.postDelayed({
                refreshTasks()
                swipeRefreshLayout.isRefreshing = false
            }, 500)
            tvTask.visibility = View.VISIBLE
            tvTaskDesc.visibility = View.VISIBLE
            taskCardContainer.visibility = View.VISIBLE
        }

        taskHistoryBtn.setOnClickListener {
            tvTask.visibility = View.GONE
            tvTaskDesc.visibility = View.GONE
            taskCardContainer.visibility = View.GONE
            val taskHistoryFragment = TaskHistoryFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.task_container, taskHistoryFragment)
                .addToBackStack(null)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit()
        }
        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.postDelayed({
                refreshTasks()
                swipeRefreshLayout.isRefreshing = false
            }, 1000)
        }
    }

    private fun fetchTasks() {
        // Send a request to fetch tasks from the database
        apiService.getTasks().enqueue(object: Callback<List<Task>> {
            override fun onResponse(call: Call<List<Task>>, response: Response<List<Task>>) {
                if (response.isSuccessful) {
                    val taskList  = response.body()!!
                    for (task in taskList) {
                        createTaskCard(taskCardContainer, task.task_title, task.id, task.is_completed)
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
                if (response.isSuccessful) {
                    Toast.makeText(requireContext(), "Successfully updated task", Toast.LENGTH_LONG).show()
                } else {
                    val errorResponse = response.errorBody()?.string()
                    Toast.makeText(requireContext(), "Error updating task: $errorResponse", Toast.LENGTH_LONG).show()
                }
            }
            override fun onFailure(call: Call<TaskResponse>, t: Throwable) {
                Toast.makeText(requireContext(), "Fatal error: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun updateTaskCompletion(id: Int, isCompleted: Int) {
        apiService.updateTaskCompletion(id, isCompleted).enqueue(object: Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(requireContext(), "Task completion status updated", Toast.LENGTH_LONG).show()
                } else {
                    val errorResponse = response.errorBody()?.string()
                    Toast.makeText(requireContext(), "$errorResponse", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(requireContext(), "Internet error occurred", Toast.LENGTH_LONG).show()
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
                        addTaskBtn.isEnabled = true
                    }
                } else {
                    val errorResponse = response.errorBody()?.string()
                    Toast.makeText(requireContext(), "$errorResponse", Toast.LENGTH_LONG).show()
                    addTaskBtn.isEnabled = true
                }
            }

            override fun onFailure(call: Call<TaskResponse>, t: Throwable) {
                Toast.makeText(requireContext(), "Internet error occured", Toast.LENGTH_LONG).show()
                addTaskBtn.isEnabled = true
            }
        })
    }

    private fun deleteTask(id: Int) {
        apiService.deleteTask(id).enqueue(object : Callback<TaskDeleteResponse> {
            override fun onResponse(call: Call<TaskDeleteResponse>, response: Response<TaskDeleteResponse>) {
                if (response.isSuccessful) {
                    Toast.makeText(requireContext(), "Successfully deleted task", Toast.LENGTH_LONG).show()
                } else {
                    val errorResponse = response.errorBody()?.string()
                    Toast.makeText(requireContext(), "Failed to delete task: $errorResponse", Toast.LENGTH_LONG).show()
                }
            }
            override fun onFailure(call: Call<TaskDeleteResponse>, t: Throwable) {
                Toast.makeText(requireContext(), "Internet error occurred", Toast.LENGTH_LONG).show()
            }
        })
    }


    // FOR CREATING A NEW NON-EMPTY TASK CARD
    private fun createTaskCard(parentLayout: LinearLayout, taskTitle: String, id: Int, isCompleted: Int) {
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
                200
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

            if (isCompleted == 0) {
                isChecked = false
            } else {
                isChecked = true
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

        val secondLinearLayout = LinearLayout(requireContext()).apply {
            layoutParams = LinearLayout.LayoutParams(
                850,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            // Remove gravity to avoid centering
            gravity = Gravity.BOTTOM
            orientation = LinearLayout.HORIZONTAL
        }

        val idField = TextView(requireContext()).apply {
            layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            ).apply {
                marginStart = 10
            }

            background = null
            text = "Task ID: $id"
            setTextColor(resources.getColor(R.color.lightgray, null))
        }

        // Focus change listener for the input field
        var previousText = inputField.text.toString()
        inputField.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val currentText = inputField.text.toString()
                if (previousText != currentText) {
                    updateTask(id, currentText)
                    previousText = currentText
                }
            }
        }

        // Hide keyboard when touching outside
        task_container.setOnTouchListener { _, _ ->
            if (inputField.isFocused) {
                inputField.clearFocus()
                val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(inputField.windowToken, 0)
            }
            false
        }

        parentLayout.setOnTouchListener { _, _ ->
            if (inputField.isFocused) {
                inputField.clearFocus()
                val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(inputField.windowToken, 0)
            }
            false
        }

        // Checkbox listener for completion status
        checkbox.setOnCheckedChangeListener { _, isChecked ->
            updateTaskCompletion(id, if (isChecked) 1 else 0)
        }

        // Editor action listener for the input field
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

        // Delete button
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
            swipeRefreshLayout.isRefreshing = true
            swipeRefreshLayout.postDelayed({
                refreshTasks()
                swipeRefreshLayout.isRefreshing = false
            }, 500)
        }

        // Add views to the layouts
        linearLayout.addView(checkbox)
        linearLayout.addView(inputField)
        linearLayout.addView(deleteBtn)

        // Add the idField to the secondLinearLayout
        secondLinearLayout.addView(idField)

        // Add both layouts to the taskCardView
        taskCardView.addView(linearLayout) // Add the first layout
        taskCardView.addView(secondLinearLayout) // Add the second layout below the first
        parentLayout.addView(taskCardView, 0) // Display task at the top of the add task button  // Display task at the top of the add task button
    }
}
