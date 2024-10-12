<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;
use Illuminate\Support\Facades\Hash; // For hashing passwords
use App\Models\FokusApp;
use App\Models\NotesModel; 
use App\Models\TaskModel; 
use Laravel\Sanctum\HasApiTokens;

class AuthController extends Controller
{
    use HasApiTokens; // Use HasApiTokens trait for token functionalities

    public function signup(Request $request)
    {
        // Validate the incoming request data
        $request->validate([
            'username' => 'required|string|unique:fokus_app,username',
            'email' => 'required|string|email|unique:fokus_app,email',
            'password' => 'required|string|min:8',
        ]);

        // Create the user and hash the password
        $user = FokusApp::create([
            'username' => $request->username,
            'email' => $request->email,
            'password' => Hash::make($request->password), // Hashing the password
        ]);

        return response()->json(['message' => 'Account created successfully!'], 201);
    }

    public function login(Request $request)
    {
        // Validate the login request data
        $request->validate([
            'email' => 'required|string|email',
            'password' => 'required|string',
        ]);

        // Attempt to log in the user
        $credentials = $request->only('email', 'password');
        $user = FokusApp::where('email', $credentials['email'])->first();

        if ($user && Hash::check($credentials['password'], $user->password)) {
            // Authentication passed, generate token
            $token = $user->createToken('FokusApp')->plainTextToken; // Create token

            // Manual login for user
            Auth::login($user);

            // Optionally create notes and tasks if provided in the request
            if ($request->has('title') && $request->has('content')) {
                // Insert a note into the database
                NotesModel::create([
                    'fokus_app_id' => $user->id,
                    'title' => $request->title, // Title from request
                    'content' => $request->content, // Content from request
                ]);
            }

            if ($request->has('task_name') && $request->has('is_completed')) {
                // Insert a task into the database
                TaskModel::create([
                    'fokus_app_id' => $user->id,
                    'task_title' => $request->task_name, // Task name from request
                ]);
            }

            return response()->json(['message' => 'Login successful', 'token' => $token], 200);
        }

        return response()->json(['message' => 'Invalid credentials'], 401);
    }
}
