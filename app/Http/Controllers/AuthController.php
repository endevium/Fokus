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
        $request->validate([
            'username' => 'required|string|unique:fokus_app,username',
            'email' => 'required|string|email|unique:fokus_app,email',
            'password' => 'required|string|min:8',
        ]);

        // Create user and hash the password
        $user = FokusApp::create([
            'username' => $request->username,
            'email' => $request->email,
            'password' => Hash::make($request->password), // Hashing the password
        ]);

        if ($user->id) {
            $token = $user->createToken('FokusApp')->plainTextToken; // Create token

            // Manual login for user
            Auth::login($user);

            if ($request->has('title') && $request->has('content')) {
                NotesModel::create([
                    'fokus_app_id' => $user->id,
                    'title' => $request->title, 
                    'content' => $request->content, 
                ]);
            }

            if ($request->has('task_name') && $request->has('is_completed')) {
               
                TaskModel::create([
                    'fokus_app_id' => $user->id,
                    'task_title' => $request->task_name, 
                    'is_completed' => $request->is_completed, 
                ]);
            }

            // Return the response with the user object and token
            return response()->json([
                'message' => 'Account created successfully!', 
                'token' => $token, 
                'data' => $user
            ], 201);
        }

        // If user creation fails, return error
        return response()->json(['message' => 'Account creation failed.'], 400);
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
            $token = $user->createToken('FokusApp')->plainTextToken; //TOKEN CREATION

            // Manual login
            Auth::login($user);

            
            $loggedin = [
                'id' => $user->id,
                'username' => $user->username,
                'email' => $user->email
            ];

            
            if ($request->has('title') && $request->has('content')) {
                // Insert a note
                NotesModel::create([
                    'fokus_app_id' => $user->id,
                    'title' => $request->title, 
                    'content' => $request->content, 
                ]);
            }

            if ($request->has('task_name') && $request->has('is_completed')) {
               
                TaskModel::create([
                    'fokus_app_id' => $user->id,
                    'task_title' => $request->task_name, 
                    'is_completed' => $request->is_completed, 
                ]);
            }

            return response()->json(['message' => 'Login successful', 'token' => $token, 'data' => $loggedin], 200);
        }

        return response()->json(['message' => 'Invalid credentials'], 401);
    }
}
