package com.example.fokus.fragments

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.fokus.R

class TaskFragment : Fragment() {
    private lateinit var taskCardContainer: LinearLayout
    private lateinit var addTaskBtn: ImageButton

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

        // Create a new task if clicked
        addTaskBtn.setOnClickListener {
            createTaskCard(taskCardContainer)
        }
    }

    // FOR CREATING A NEW TASK
    private fun createTaskCard(parentLayout: LinearLayout) {
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
