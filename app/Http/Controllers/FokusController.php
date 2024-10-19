<?php

namespace App\Http\Controllers;

use App\Models\FokusApp;
use App\Models\TaskModel;
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
            'username' => [
                'required',
                'string',
                'max:255',
                'unique:fokus_app',
                'regex:/^[A-Za-z0-9][A-Za-z0-9!@#$%^&*()_+=-]*$/', // Username must not contain spaces or multiple special characters
            ],
            'password' => [
                'required',
                'string',
                'min:8',
                'max:50',
                'regex:/^(?!.*\s)(?!.*[!@#$%^&*()_+=-].*[!@#$%^&*()_+=-]).*$/', // Password must not contain spaces or multiple special characters
            ],
            'email' => [
                'required',
                'string',
                'email',
                'max:255',
                'unique:fokus_app',
                'regex:/^[a-zA-Z0-9._%+-]+@\.com$/'
            ],
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
            'username' => [
                'sometimes',
                'required',
                'string',
                'max:255',
                'unique:fokus_app,username,' . $fokusApp->id,
                'regex:/^[A-Za-z0-9][A-Za-z0-9!@#$%^&*()_+=-]*$/', // Username must not contain spaces or multiple special characters
            ],
            'password' => [
                'sometimes',
                'required',
                'string',
                'min:8',
                'max:50',
                'regex:/^(?!.*\s)(?!.*[!@#$%^&*()_+=-].*[!@#$%^&*()_+=-]).*$/'
            ],
            'email' => [
                'required',
                'string',
                'email',
                'max:255',
                'unique:fokus_app,email,' . $fokusApp->id,
                'regex:/^[a-zA-Z0-9._%+-]+@\.com$/'

            ],
        ], [
            'password.regex' => 'The password must contain no spaces and at most one special character.',
            'username.regex' => 'The username must not contain spaces or multiple special characters.',
            'email.regex' => 'Must be a valid email.',
        ]);

        // Update the fields only if they are present in the request
        if ($request->has('password')) {
            // Hash the new password and update it
            $fokusApp->password = Hash::make($request->password);
        }

        if ($request->has('username')) {
            // Check if the new username is the same as the current username
            if ($request->username === $fokusApp->username) {
                return response()->json(['message' => 'New username must be different from the current username'], 400);
            }
            
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

    // CHANGE PASSWORD
    public function changePassword(Request $request)
    {
        // Validate incoming request data
        $request->validate([
            'email' => 'required|string|email',
            'new_password' => 'required|string|min:16',
        ]);

        // Find user by email
        $user = FokusApp::where('email', $request->email)->first();

        if (!$user) {
            return response()->json(['message' => 'Email not found'], 404);
        }

        // Check if the new password is the same as the current password
        if (Hash::check($request->new_password, $user->password)) {
            return response()->json(['message' => 'New password must be different from the current password'], 400);
        }

        // Hash new password and update it
        $user->password = Hash::make($request->new_password);
        $user->save();

        return response()->json(['message' => 'Password changed successfully!'], 200);
    }

    // TASK METHOD
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

        // Update task completion status
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
