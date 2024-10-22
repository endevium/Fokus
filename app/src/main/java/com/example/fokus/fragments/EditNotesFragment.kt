package com.example.fokus

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.fokus.api.*
import com.example.fokus.fragments.NotesFragment
import com.example.fokus.fragments.SharedViewModel
import com.example.fokus.models.NotesResponse
import retrofit2.*

class EditNotesFragment : Fragment (R.layout.fragment_editnotes){
    private lateinit var backBtn: ImageButton
    private lateinit var title: EditText
    private lateinit var content: EditText
    private lateinit var apiService: APIService
    private lateinit var notesFragment: NotesFragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        backBtn = view.findViewById(R.id.backBtn)
        title = view.findViewById(R.id.title)
        content = view.findViewById(R.id.content)
        apiService = RetrofitClient.create(APIService::class.java)
        notesFragment = NotesFragment()

        val args = this.arguments
        val id = args?.getInt("id")
        val fetchedTitle = args?.getString("title")
        val fetchedContent = args?.getString("content")
        val viewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]

        if (fetchedTitle != "Note Title") title.setText(fetchedTitle) else title.text = null
        if (fetchedContent != "Note Description") content.setText(fetchedContent) else content.text = null

        viewModel.textColor.observe(viewLifecycleOwner, Observer { color ->
            title.setTextColor(color)
        })

        backBtn.setOnClickListener {
            if (id != null) {
                val updatedTitle = if (title.text.isNotEmpty()) title.text.toString() else "Note Title"
                val updatedContent = if (content.text.isNotEmpty()) content.text.toString() else "Note Description"

                parentFragmentManager.setFragmentResult("noteUpdated", Bundle())

                updateNote(id, updatedTitle, updatedContent)
            }

            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    private fun updateNote(id: Int, title: String, content: String) {
        apiService.updateNote(id, title, content).enqueue(object: Callback<NotesResponse> {
            override fun onResponse(call: Call<NotesResponse>, response: Response<NotesResponse>) {
                if (!response.isSuccessful) {
                    val errorResponse = response.errorBody()?.string()
                    Toast.makeText(requireContext(), "Error updating: $errorResponse", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<NotesResponse>, t: Throwable) {
                Toast.makeText(requireContext(), "Fatal error: ${t.message}", Toast.LENGTH_LONG).show()
            }

        })
    }

    override fun onDestroyView() {
        super.onDestroyView()

        // Manually trigger onResume actions in NotesFragment when returning to it
        val fragmentManager = requireActivity().supportFragmentManager
        val fragment = fragmentManager.findFragmentByTag("NotesFragment") as? NotesFragment
        fragment?.onResume() // This forces the onResume behavior
    }
}

