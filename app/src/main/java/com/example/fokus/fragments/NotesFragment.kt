package com.example.fokus.fragments

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.*
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

class NotesFragment : Fragment() {
    private lateinit var tvNotes: TextView
    private lateinit var tvNotesDesc: TextView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var notesCardLayout: LinearLayout
    private lateinit var addNoteBtn: LinearLayout
    private lateinit var apiService: APIService
    private lateinit var editNotesFrag: EditNotesFragment
    private lateinit var notes_container: RelativeLayout
    private val notesList: MutableList<Notes> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_notes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]

        notesCardLayout = view.findViewById(R.id.notesCardLayout)
        addNoteBtn = view.findViewById(R.id.addnoteBtn)
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout)
        apiService = RetrofitClient.create(APIService::class.java)
        editNotesFrag = EditNotesFragment()
        tvNotes = view.findViewById(R.id.tvNotes)
        tvNotesDesc = view.findViewById(R.id.tvNotesDesc)
        notes_container = view.findViewById(R.id.notes_container)

        fetchNotes()

        parentFragmentManager.setFragmentResultListener("noteUpdated", viewLifecycleOwner) { _, _ ->
            notesCardLayout.removeAllViews()
            fetchNotes()

            tvNotes.visibility = View.VISIBLE
            tvNotesDesc.visibility = View.VISIBLE
            notesCardLayout.visibility = View.VISIBLE
            addNoteBtn.visibility = View.VISIBLE
        }

        addNoteBtn.setOnClickListener {
            // Create an empty note card when add button is clicked
            createNote("Note Title", "Note Description")
        }

        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.postDelayed({
                notesCardLayout.removeAllViews()
                fetchNotes()

                swipeRefreshLayout.isRefreshing = false
            }, 1000)
        }

        viewModel.textColor.observe(viewLifecycleOwner, Observer { color ->
            tvNotes.setTextColor(color)
            tvNotesDesc.setTextColor(color)
        })

    }

    private fun fetchNotes() {
        // Send a request to get/fetch notes from the database
        apiService.getNotes().enqueue(object: Callback<List<Notes>> {
            // Modify on response function
            override fun onResponse(call: Call<List<Notes>>, response: Response<List<Notes>>) {
                if (response.isSuccessful) {
                    notesList.clear()
                    notesList.addAll(response.body()!!)
                    displayNotes()
                } else {
                    Toast.makeText(requireContext(), "Fetching notes failed", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Notes>>, t: Throwable) {
                Toast.makeText(requireContext(), "Fetching notes failed: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun displayNotes() {
        notesCardLayout.removeAllViews()
        for (note in notesList) {
            createNoteCard(notesCardLayout, note.id, note.title, note.content)
        }
    }

    private fun createNote(title: String, content: String) {
        apiService.createNote(title, content).enqueue(object: Callback<NotesResponse> {
            override fun onResponse(call: Call<NotesResponse>, response: Response<NotesResponse>) {
                if (response.isSuccessful) {
                    val createNoteResponse = response!!.body()

                    if (createNoteResponse != null) {
                        notesCardLayout.removeAllViews()

                        fetchNotes()
                        Toast.makeText(requireContext(), "New note created", Toast.LENGTH_LONG).show()
                    }
                } else {
                    val errorResponse = response.errorBody()?.string()
                    Toast.makeText(requireContext(), "Error creating new note: $errorResponse", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<NotesResponse>, t: Throwable) {
                Toast.makeText(requireContext(), "Fatal error: ${t.message}", Toast.LENGTH_LONG).show()
            }

        })
    }

    private fun deleteNote(id: Int) {
        apiService.deleteNote(id).enqueue(object: Callback<NotesResponse> {
            override fun onResponse(call: Call<NotesResponse>, response: Response<NotesResponse>) {
                if (response.isSuccessful) {
                    Toast.makeText(requireContext(), "Successfully deleted note", Toast.LENGTH_LONG).show()
                } else {
                    val errorResponse = response.errorBody()?.string() ?: "Unknown error"
                    Toast.makeText(requireContext(), "Failed to delete note: $errorResponse", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<NotesResponse>, t: Throwable) {
                Toast.makeText(requireContext(), "Deleting note failed: ${t.message}", Toast.LENGTH_LONG).show()
            }

        })
    }

    // FOR FETCHED NOTES
    private fun createNoteCard(parentLayout: LinearLayout, id: Int, title: String, content: String) {
        // Note card layout
        val noteCardView = CardView(requireContext()).apply {
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

        // Linear layout holder for title and description
        val firstLinearLayout = LinearLayout(requireContext()).apply {
            layoutParams = LinearLayout.LayoutParams(
                850,
                450
            )
            background = ContextCompat.getDrawable(requireContext(), R.drawable.ncontainer)
            orientation = LinearLayout.VERTICAL
        }

        // Note title layout
        val noteTitle = TextView(requireContext()).apply {
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
            text = title
            setTextColor(resources.getColor(R.color.lightblack, null))
            textSize = 16f
            setTypeface(null, Typeface.BOLD)
        }

        val closeBtn = ImageButton(requireContext()).apply {
            layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            ).apply {
                marginStart = 760
                setMargins(0, -90, 0, 0)
            }

            setBackgroundColor(Color.TRANSPARENT)
            setImageResource(R.drawable.close)
        }

        closeBtn.setOnClickListener {
            deleteNote(id)
            fetchNotes()
        }

        val secondLinearLayout = LinearLayout(requireContext()).apply {
            layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                120
            ).apply {
                setMargins(0, 0, 0, 0)
            }

            orientation = LinearLayout.HORIZONTAL

        }

        // Note description layout
        val noteDescription = TextView(requireContext()).apply {
            layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            ).apply {
                marginStart = 30
                setMargins(0, -10, 0, 0)
            }

            // Change visual layout
            background = null
            text = content
        }

        // Another linear layout for the edit button
        val thirdLinearLayout = LinearLayout(requireContext()).apply {
            layoutParams = LinearLayout.LayoutParams(
                225,
                90
            ).apply {
                marginStart = 595
                marginEnd = 60
                setMargins(0, 70, 0, 0)
            }
            // Change visual layout

            background = ContextCompat.getDrawable(requireContext(), R.drawable.econtainer)
        }

        // Edit button layout
        val editButton = TextView(requireContext()).apply {
            layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )

            // Move button to the center of the layout
            gravity = Gravity.CENTER

            // Change text style
            text = "Edit"
            textAlignment = View.TEXT_ALIGNMENT_CENTER
        }

        editButton.setOnClickListener {
            tvNotes.visibility = View.GONE
            tvNotesDesc.visibility = View.GONE
            notesCardLayout.visibility = View.GONE
            addNoteBtn.visibility = View.GONE

            val newEditNotesFrag = EditNotesFragment()
            newEditNotesFrag.arguments = Bundle().apply {
                putInt("id", id)
                putString("title", title)
                putString("content", content)
            }

            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.notes_container, newEditNotesFrag)
                .addToBackStack(null)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN) // Add transition effect
                .commit()
        }

        firstLinearLayout.addView(noteTitle) // Add note title
        firstLinearLayout.addView(closeBtn) // Add close button
        firstLinearLayout.addView(secondLinearLayout) // Add second linear layout for note description
        secondLinearLayout.addView(noteDescription) // Add note description inside second linear layout
        thirdLinearLayout.addView(editButton) // Add edit button to the third linear layout
        firstLinearLayout.addView(thirdLinearLayout) // Finally, add the third layout containing the edit button
        noteCardView.addView(firstLinearLayout) // Wrap all elements in the card
        parentLayout.addView(noteCardView) // Display the card
    }

    override fun onStart() {
        super.onStart()
        fetchNotes()
    }

    override fun onResume() {
        super.onResume()
        fetchNotes()
    }
}
