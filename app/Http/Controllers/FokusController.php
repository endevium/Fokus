<?php

namespace App\Http\Controllers;

use App\Models\FokusApp;
use App\Models\FokusTask; // Import the FokusTask model
use App\Models\TaskModel;
use App\Models\PasswordHistory; // Import the PasswordHistory model
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Hash;

class FokusController extends Controller
{
    public function index()
    {
        // Fetch all users from the FokusApp model
        $users = FokusApp::all();

        return response()->json($users);
    }

    public function store(Request $request)
    {
        $messages = [
            'username.unique' => 'The username has already been taken.',
            'email.unique' => 'The email has already been registered.',
        ];

        // Validate incoming request data
        $request->validate([
            'username' => 'required|string|max:255|unique:fokus_app',
            'password' => 'required|string|max:50',
            'email' => 'required|string|email|max:255|unique:fokus_app',
        ], $messages);

        // Create a new user and hash the password
        $fokusApp = new FokusApp([
            'username' => $request->username,
            'password' => Hash::make($request->password),
            'email' => $request->email,
        ]);

        $fokusApp->save();

        return response()->json(['message' => 'User created successfully!', 'data' => $fokusApp], 201);
    }

    public function show($id)
    {
        // Fetch a specific user by ID
        $fokusApp = FokusApp::find($id);

        if (!$fokusApp) {
            return response()->json(['message' => 'User not found'], 404);
        }

        return response()->json($fokusApp);
    }

    public function update(Request $request, $id)
    {
        $fokusApp = FokusApp::find($id);

        if (!$fokusApp) {
            return response()->json(['message' => 'User not found'], 404);
        }

        $request->validate([
            'username' => 'sometimes|required|string|max:255|unique:fokus_app,username,' . $fokusApp->id,
            'password' => 'sometimes|required|string|max:50',
            'email' => 'sometimes|required|string|email|max:255|unique:fokus_app,email,' . $fokusApp->id,
        ]);

        // Check for password reuse
        if ($request->has('password')) {
            $passwordExists = PasswordHistory::where('user_id', $fokusApp->id)
                ->get()
                ->contains(function ($history) use ($request) {
                    return Hash::check($request->password, $history->password);
                });

            if ($passwordExists) {
                return response()->json(['message' => 'You cannot reuse a recent password.'], 422);
            }

            // Save the old password to the password history
            PasswordHistory::create([
                'user_id' => $fokusApp->id,
                'password' => $fokusApp->password,
            ]);

            // Hash the new password and update it
            $fokusApp->password = Hash::make($request->password);
        }

        // Update the fields only if they are present in the request
        if ($request->has('username')) {
            $fokusApp->username = $request->username;
        }
        if ($request->has('email')) {
            $fokusApp->email = $request->email;
        }

        $fokusApp->save();

        return response()->json(['message' => 'User updated successfully!', 'data' => $fokusApp]);
    }

    public function destroy($id)
    {
        $fokusApp = FokusApp::find($id);

        if (!$fokusApp) {
            return response()->json(['message' => 'User not found'], 404);
        }

        $fokusApp->delete();

        return response()->json(['message' => 'User deleted successfully!']);
    }

    public function login(Request $request)
    {
        $request->validate(['email' => 'required|string|email', 'password' => 'required|string']);

        $user = FokusApp::where('email', $request->email)->first();

        if (!$user) {
            return response()->json(['success' => false, 'message' => 'Email not found'], 404);
        }

        if (Hash::check($request->password, $user->password)) {
            $token = $user->createToken('Fokus_App')->plainTextToken;
            return response()->json(['success' => true, 'message' => 'Login successful', 'user' => $user, 'token' => $token], 200);
        } else {
            return response()->json(['success' => false, 'message' => 'Invalid password'], 401);
        }
    }



    
    //TASK METHOD
    public function completeTask(Request $request, $id)
    {
        $request->validate([
            'is_completed' => 'required|boolean',
        ]);

        // Find the task by ID
        $task = TaskModel::find($id);

        if (!$task) {
            return response()->json(['message' => 'Task not found'], 404);
        }

        // Update  task completion status
        $task->is_completed = $request->is_completed; // true or false based on request
        $task->save(); 

        return response()->json(['message' => 'Task completion status updated successfully!', 'task' => $task], 200);
    }

    public function checkTaskCompletion($id)
    {
        $task = TaskModel::find($id);

        if (!$task) {
            return response()->json(['message' => 'Task not found'], 404);
        }

        $status = $task->is_completed ? 'complete' : 'incomplete';
        return response()->json(['message' => 'Task is ' . $status, 'task' => $task], 200);
    }
}
