<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;
use Illuminate\Support\Facades\Hash;
use App\Models\FokusApp;
use App\Models\NotesModel;
use App\Models\TaskModel;
use Laravel\Sanctum\HasApiTokens;

class AuthController extends Controller
{
    use HasApiTokens;

    public function signup(Request $request)
    {
        $messages = [
            'username.required' => 'Username is required.',
            'username.unique' => 'The username has already been taken.',
            'username.regex' => 'Username must start with an alphanumeric character, allow specific special characters, and contain no spaces.',
            'email.required' => 'Email is required.',
            'email.unique' => 'The email has already been registered.',
            'email.regex' => 'Email must be valid email.',
            'password.required' => 'Password is required.',
            'password.regex' => 'Password must be at least 8 characters, contain no spaces, and only one special character.',
        ];
        $request->validate([
            'username' => [
                'required',
                'string',
                'unique:fokus_app,username',
                'regex:/^(?=.*[a-z])[^\\s!@#$%^&*()_+=-]*(?:[!@#$%^&*()_+=-]?[^\\s!@#$%^&*()_+=-]*)*$/' //no rules in characters and no spamming special characters, no spaces
            ],
            'email' => [
                'required',
                'string',
                'email:rfc,dns',
                'max:255', // Length check
                'unique:fokus_app,email',

            ],
            'password' => [
                'required',
                'string',
                'min:8',
                'regex:/^(?!.*\s)(?!.*[!@#$%^&*()_+=-]{2}).*$/', // No spaces, prevents repeated special characters
            ],
        ], $messages);

        $user = FokusApp::create([
            'username' => $request->username,
            'email' => $request->email,
            'password' => Hash::make($request->password),
        ]);

        if ($user->id) {
            $token = $user->createToken('FokusApp')->plainTextToken;

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

            return response()->json([
                'message' => 'Account created successfully!', 
                'token' => $token, 
                'data' => $user
            ], 201);
        }

        return response()->json(['message' => 'Account creation failed.'], 400);
    }

    public function login(Request $request)
    {
        $request->validate([
            'email' => [
                'required',
                'string',
                'email:rfc,dns',
                'max:255',
            ],
            'password' => [
                'required',
                'string',
                'regex:/^(?!.*\s)(?!.*[!@#$%^&*()_+=-]{2}).*$/' // No spaces, no repeating special characters
            ],
        ]);

        $credentials = $request->only('email', 'password');
        $user = FokusApp::where('email', $credentials['email'])->first();

        if ($user && Hash::check($credentials['password'], $user->password)) {
            $token = $user->createToken('FokusApp')->plainTextToken;

            Auth::login($user);

            $loggedin = [
                'id' => $user->id,
                'username' => $user->username,
                'email' => $user->email
            ];


                //FOKUS_NOTES CREATION
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

            return response()->json([
                'message' => 'Login successful', 
                'token' => $token, 
                'data' => $loggedin
            ], 200);
        }

        return response()->json(['message' => 'Invalid credentials'], 401);
    }
}
