<?php

namespace App\Http\Controllers;

use App\Models\FokusApp;
use App\Models\TaskModel;
use App\Models\TaskHistory;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;
use Illuminate\Support\Facades\Hash;

class FokusController extends Controller
{
    public function index()
    {
        $users = FokusApp::all();
        return response()->json($users);
    }

    public function store(Request $request)
    {
        $messages = [
            'username.unique' => 'The username has already been taken.',
            'email.unique' => 'The email has already been registered.',
            'password.regex' => 'The password must contain no spaces and at most one special character.',
            'username.regex' => 'The username must not contain spaces or multiple special characters.',
        ];

        $request->validate([
            'username' => [
                'required',
                'string',
                'max:255',
                'unique:fokus_app',
                'regex:/^[A-Za-z0-9]+(?:[!@#$%^&*()_+=-]{0,1}[A-Za-z0-9]+)*$/'
            ],
            'password' => [
                'required',
                'string',
                'min:8',
                'max:50',
                'regex:/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*()_+=-])(?=^[^\s]{8,50}$)(?!.*[!@#$%^&*()_+=-]{2}).*$/'
            ],
            'email' => [
                'required', 'string', 'email:rfc,dns', 'max:255', 'unique:fokus_app',
            ]
        ], $messages);

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
                'max:50',
                'unique:fokus_app,username,' . $fokusApp->id,
                'regex:/^[A-Za-z0-9]+(?:[!@#$%^&*()_+=-]{0,1}[A-Za-z0-9]+)*$/'
            ],
            'password' => [
                'sometimes',
                'required',
                'string',
                'min:8',
                'max:50',
                'regex:/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*()_+=-])(?=^[^\s]{8,50}$)(?!.*[!@#$%^&*()_+=-]{2}).*$/'
            ],
            'email' => [
                'sometimes',
                'string',
                'email:rfc,dns',
                'max:255',
                'unique:fokus_app,email,' . $fokusApp->id,
            ],
        ], [
            'password.regex' => 'The password must contain no spaces and at most one special character.',
            'username.regex' => 'The username must not contain spaces or multiple special characters.',
            'email.regex' => 'Must be a valid email.',
        ]);

        $fokusApp->username = $request->username ?? $fokusApp->username;
        $fokusApp->email = $request->email ?? $fokusApp->email;

        if ($request->has('password')) {
            $fokusApp->password = Hash::make($request->password);
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
    $request->validate([
        'email' => 'required|string|email',
        'password' => [
            'required',
            'string',
            'min:8',
            'max:128',
            'regex:/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*()_+=-])(?=^[^\s]{8,50}$)(?!.*[!@#$%^&*()_+=-]{2}).*$/'
        ],
    ], [
        'password.regex' => 'The new password must contain at least one uppercase letter, one lowercase letter, one digit, one special character, and be at least 8 characters long without spaces.',
    ]);

    $user = FokusApp::where('email', $request->email)->first();

    if (!$user) {
        return response()->json(['message' => 'Email not found'], 404);
    }

    if (Hash::check($request->new_password, $user->password)) {
        return response()->json(['message' => 'New password must be different from the current password'], 400);
    }

    $user->password = Hash::make($request->new_password);
    $user->save();

    return response()->json(['message' => 'Password changed successfully!'], 200);
}


    public function completeTask(Request $request, $id)
    {
        $request->validate([
            'is_completed' => 'required|boolean',
        ]);

        $task = TaskModel::find($id);

        if (!$task) {
            return response()->json(['message' => 'Task not found'], 404);
        }

        $task->is_completed = $request->is_completed;
        $task->save(); 

        TaskHistory::create([
            'task_id' => $task->id,
            'user_id' => Auth::id(),
            'status' => 'Completed',
            'description' => 'Marked the task "' . $task->task_title . '" as ' . ($task->is_completed ? 'completed' : 'incomplete'),
        ]);

        return response()->json(['message' => 'Task completion status updated successfully!', 'task' => $task], 200);
    }

    public function checkTaskCompletion($id)
    {
        $task = TaskModel::find($id);

        if (!$task) {
            return response()->json(['message' => 'Task not found'], 404);
        }

        $status = $task->is_completed ? 'Task is already completed' : 'Task is not completed yet';
        return response()->json(['message' => $status], 200);
    }
}
