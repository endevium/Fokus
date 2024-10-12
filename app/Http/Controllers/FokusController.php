<?php

namespace App\Http\Controllers;

use App\Models\FokusApp;
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

        // Update the fields only if they are present in the request
        if ($request->has('username')) {
            $fokusApp->username = $request->username;
        }
        if ($request->has('email')) {
            $fokusApp->email = $request->email;
        }
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
}
