<?php

namespace App\Http\Controllers;

use App\Models\FokusApp;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Hash;

class fokusController extends Controller
{
    public function index()
    {
        // Fetch all users from the FokusApp model
        $users = FokusApp::all(); // Or use a paginator like FokusApp::paginate(10);
        
        return response()->json($users);
    }

    public function store(Request $request)
    {
        $request->validate([
            'username' => 'required|string|max:255|unique:fokus_app',
            'password' => 'required|string|min:8',
            'email' => 'required|string|email|max:255|unique:fokus_app',
        ]);

        $fokusApp = new FokusApp([
            'username' => $request->username,
            'password' => Hash::make($request->password),
            'email' => $request->email,
        ]);

        $fokusApp->save();

        return response()->json(['message' => 'User created successfully!', 'data' => $fokusApp], 201);
    }

    public function login(Request $request)
    {
        $request->validate(['email' => 'required|string|email', 'password' => 'required|string']);
        
        // Fetch user by email
        $user = FokusApp::where('email', $request->email)->first();
    
        if ($user && Hash::check($request->password, $user->password)) {
            // Create a token for the user
            $token = $user->createToken('YourAppName')->plainTextToken;
    
            return response()->json([
                'success' => true,
                'message' => 'Login successful',
                'user' => $user,
                'token' => $token
            ], 200);
        } else {
            return response()->json(['success' => false, 'message' => 'Invalid email or password'], 401);
        }
    }
}    
