<?php

namespace App\Http\Controllers;

use App\Models\NotesModel;
use Illuminate\Http\Request;

class notesController extends Controller
{
    public function index()
    {
        $notes = NotesModel::all();
        return response()->json($notes);
    }

    public function store(Request $request)
    {
        $request->validate([
            'title' => 'required|string|max:255',
            'content' => 'required|string',
        ]);

        $note = NotesModel::create($request->all());
        return response()->json(['message' => 'Note created successfully!', 'data' => $note], 201);
    }

    public function show($id)
    {
        $note = NotesModel::findOrFail($id);
        return response()->json($note);
    }

    public function update(Request $request, $id)
    {
        $note = NotesModel::findOrFail($id);
        $note->update($request->all());
        return response()->json(['message' => 'Note updated successfully!', 'data' => $note]);
    }

    public function destroy($id)
    {
        $note = NotesModel::findOrFail($id);
        $note->delete();
        return response()->json(['message' => 'Note deleted successfully!']);
    }
}
