package com.example.fokus.fragments

import android.graphics.Typeface
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.fokus.*
import com.example.fokus.api.*
import com.example.fokus.models.*
import retrofit2.*

class NotesFragment : Fragment() {
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var notesCardLayout: LinearLayout
    private lateinit var addNoteBtn: ImageButton
    private lateinit var apiService: APIService
    private lateinit var editNotesFrag: EditNotesFragment
    private val notesList: MutableList<Notes> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_notes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        notesCardLayout = view.findViewById(R.id.notesCardLayout)
        addNoteBtn = view.findViewById(R.id.addnoteBtn)
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout)
        apiService = RetrofitClient.create(APIService::class.java)
        editNotesFrag = EditNotesFragment()

        // Fetch notes when switched to this fragment
        fetchNotes()

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
    }

    private fun fetchNotes() {
        // Send a request to get/fetch notes from the database
        apiService.getNotes().enqueue(object: Callback<List<Notes>> {
            // Modify on response function
            override fun onResponse(call: Call<List<Notes>>, response: Response<List<Notes>>) {
                if (response.isSuccessful) {
                    notesList.clear()
                    notesList.addAll(response.body()!!)
                    // Display all fetched notes from the hashmap
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

        // Note description layout
        val noteDescription = TextView(requireContext()).apply {
            layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                60
            ).apply {
                marginStart = 30
                setMargins(0, 15, 0, 0)
            }

            // Change visual layout
            background = null
            text = content
        }

        // Another linear layout for the edit button
        val secondLinearLayout = LinearLayout(requireContext()).apply {
            layoutParams = LinearLayout.LayoutParams(
                225,
                90
            ).apply {
                // Move layout to the right/end
                gravity = Gravity.END
                marginEnd = 30
                setMargins(0, 160, 0, 0)
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

        secondLinearLayout.addView(editButton) // Wrap edit button first
        firstLinearLayout.addView(noteTitle)
        firstLinearLayout.addView(noteDescription)
        firstLinearLayout.addView(secondLinearLayout) // Then wrap the second linear layout along with the title and description
        noteCardView.addView(firstLinearLayout) // Wrap all elements in the card
        parentLayout.addView(noteCardView) // Display the card
    }
}
