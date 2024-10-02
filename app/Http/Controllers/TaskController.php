<?php

namespace App\Http\Controllers;

use App\Models\TaskModel; // Assuming you have a TaskModel for the tasks
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;

class TaskController extends Controller
{
    // Get all tasks for the logged-in user
    public function index()
    {
        $tasks = TaskModel::where('fokus_app_id', Auth::id())->get(); // Retrieve tasks for the logged-in user
        return response()->json($tasks);
    }

    // Store a new task
    public function store(Request $request)
    {
        $request->validate([
            'task_title' => 'required|string|max:255',
        ]);

        // Generate a token (you can customize this logic)
        $token = bin2hex(random_bytes(16)); // Generates a random token

        // Create a new task and associate it with the logged-in user
        $task = new TaskModel();
        $task->task_title = $request->task_title;
        $task->token = $token; // Save the generated token with the task
        $task->fokus_app_id = Auth::id(); // Associate with logged-in user

        try {
            $task->save();
        } catch (\Exception $e) {
            return response()->json(['message' => 'Failed to create task.'], 500);
        }

        return response()->json(['message' => 'Task created successfully!', 'data' => $task], 201);
    }

    // Show a specific task
    public function show($id)
    {
        $task = TaskModel::where('id', $id)
            ->where('fokus_app_id', Auth::id())
            ->firstOrFail(); // Ensure the task belongs to the user
        return response()->json($task);
    }

    // Update a specific task
    public function update(Request $request, $id)
    {
        $task = TaskModel::where('id', $id)
            ->where('fokus_app_id', Auth::id())
            ->firstOrFail();

        $request->validate([
            'task_title' => 'required|string|max:255',
        ]);

        // Update the task with validated data
        $task->update($request->only(['task_title']));

        return response()->json(['message' => 'Task updated successfully!', 'data' => $task]);
    }

    // Delete a task
    public function destroy($id)
    {
        $task = TaskModel::where('id', $id)
            ->where('fokus_app_id', Auth::id())
            ->firstOrFail();
        $task->delete();

        return response()->json(['message' => 'Task deleted successfully!']);
    }
}
