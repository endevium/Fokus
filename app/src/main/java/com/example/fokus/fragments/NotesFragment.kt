package com.example.fokus.fragments

import android.graphics.Typeface
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.fokus.*
import com.example.fokus.api.*
import com.example.fokus.models.*
import retrofit2.*

class NotesFragment : Fragment() {
    private lateinit var notesCardLayout: LinearLayout
    private lateinit var addNoteBtn: ImageButton
    private lateinit var apiService: APIService
    private lateinit var editNotesFrag: EditNotesFragment
    private val hmNotes: HashMap<String, String> = HashMap()

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
        apiService = RetrofitClient.create(APIService::class.java)
        editNotesFrag = EditNotesFragment()

        // Fetch notes when switched to this fragment
        fetchNotes()

        addNoteBtn.setOnClickListener {
            // Create an empty note card when add button is clicked
            createEmptyNoteCard(notesCardLayout)
        }
    }

    private fun fetchNotes() {
        // Send a request to get/fetch notes from the database
        apiService.getNotes().enqueue(object: Callback<List<Notes>> {
            // Modify on response function
            override fun onResponse(call: Call<List<Notes>>, response: Response<List<Notes>>) {
                if (response.isSuccessful) {
                    val notesList = response.body()!!
                    // Copy all notes to a HashMap
                    for (note in notesList) {
                        hmNotes[note.title] = note.content
                    }
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
        // Create a note card for each notes
        for ((title, content) in hmNotes) {
            createNoteCard(notesCardLayout, title, content)
        }
    }


    // FOR FETCHED NOTES
    private fun createNoteCard(parentLayout: LinearLayout, title: String, content: String) {
        // Note card layout
        val noteCardView = CardView(requireContext()).apply {
            id = View.generateViewId()
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
                ViewGroup.LayoutParams.WRAP_CONTENT
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
            val bundle = Bundle()
            bundle.putString("title", title)
            bundle.putString("content", content)

            editNotesFrag.arguments = bundle
            editNotesFragment()
        }

        secondLinearLayout.addView(editButton) // Wrap edit button first
        firstLinearLayout.addView(noteTitle)
        firstLinearLayout.addView(noteDescription)
        firstLinearLayout.addView(secondLinearLayout) // Then wrap the second linear layout along with the title and description
        noteCardView.addView(firstLinearLayout) // Wrap all elements in the card
        parentLayout.addView(noteCardView) // Display the card
    }

    // CREATING A NEW NOTE
    private fun createEmptyNoteCard(parentLayout: LinearLayout) {
        // Empty note card layout
        val noteCardView = CardView(requireContext()).apply {
            id = View.generateViewId()
            layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(0, 0, 0, 40)
            }
            radius = 30f
            cardElevation = 14f
            setCardBackgroundColor(resources.getColor(R.color.white, null))
        }

        // Linear layout for title and description
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
                setGravity(Gravity.START)
                marginStart = 30
                setMargins(0, 30, 0, 0)
            }
            background = null
            text = "Note Title"
            setTextColor(resources.getColor(R.color.lightblack, null))
            textSize = 16f
            setTypeface(null, Typeface.BOLD)
        }

        // Note description layout
        val noteDescription = TextView(requireContext()).apply {
            layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            ).apply {
                marginStart = 30
                setMargins(0, 15, 0, 0)
            }
            background = null
            setHint("Note Description")
        }

        // Another linear layout for the edit button
        val secondLinearLayout = LinearLayout(requireContext()).apply {
            layoutParams = LinearLayout.LayoutParams(
                225,
                90
            ).apply {
                gravity = Gravity.END
                marginEnd = 30
                setMargins(0, 160, 0, 0)
            }
            background = ContextCompat.getDrawable(requireContext(), R.drawable.econtainer)
        }

        // Edit button layout
        val editButton = TextView(requireContext()).apply {
            layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            gravity = Gravity.CENTER
            text = "Edit"
            textAlignment = View.TEXT_ALIGNMENT_CENTER
        }

        editButton.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("title", "Note Title")
            bundle.putString("content", "Start typing here...")

            editNotesFrag.arguments = bundle
            editNotesFragment()
        }

        secondLinearLayout.addView(editButton) // Wrap edit button first
        firstLinearLayout.addView(noteTitle)
        firstLinearLayout.addView(noteDescription)
        firstLinearLayout.addView(secondLinearLayout) // Then wrap it together with the title and description
        noteCardView.addView(firstLinearLayout) // Wrap all of the elements in the card
        parentLayout.addView(noteCardView) // Display the card
    }

    private fun editNotesFragment() {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.notes_container, editNotesFrag) // Replace current fragment
            .addToBackStack(null) // Add to back stack to allow back navigation
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN) // Add transition effect
            .commit()
    }
}