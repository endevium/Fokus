<?php

namespace App\Http\Controllers;

use App\Models\NotesModel;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;

class NotesController extends Controller
{
    public function index()
    {
        $notes = NotesModel::where('fokus_app_id', Auth::id())->get(); // Retrieve notes for the logged-in user
        return response()->json($notes);
    }

    public function store(Request $request)
    {
        $request->validate([
            'title' => 'required|string|max:255',
            'content' => 'required|string',
        ]);

        // Create a new note and associate it with the logged-in user
        $note = new NotesModel();
        $note->title = $request->title;
        $note->content = $request->content;
        $note->fokus_app_id = Auth::id(); // Associate with logged-in user
        $note->save();

        return response()->json(['message' => 'Note created successfully!', 'data' => $note], 201);
    }

    public function show($id)
    {
        $note = NotesModel::where('id', $id)->where('fokus_app_id', Auth::id())->firstOrFail();
        return response()->json($note);
    }

    public function update(Request $request, $id)
    {
        $note = NotesModel::where('id', $id)->where('fokus_app_id', Auth::id())->firstOrFail();
        $note->update($request->all());
        return response()->json(['message' => 'Note updated successfully!', 'data' => $note]);
    }

    public function destroy($id)
    {
        $note = NotesModel::where('id', $id)->where('fokus_app_id', Auth::id())->firstOrFail();
        $note->delete();
        return response()->json(['message' => 'Note deleted successfully!']);
    }
}
