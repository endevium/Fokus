<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Storage;
use Illuminate\Support\Facades\Auth;
use App\Models\FokusApp;

class ProfileController extends Controller
{
    public function uploadProfilePicture(Request $request)
    {
        // Check if the user is authenticated
        $user = Auth::user();
        
        if (!$user) {
            return response()->json([
                'success' => false,
                'message' => 'User not authenticated.'
            ], 401);    
        }

        $request->validate([
            'profile_picture' => 'required|image|mimes:jpg,jpeg,png|max:5120', // Max size 5MB
        ]);
    
        // Handle the upload
        if ($request->hasFile('profile_picture')) {
            $file = $request->file('profile_picture');
            $path = $file->store('profiles', 'public');
    
            // Assign profile picture path to the authenticated user
            $user->profile_picture = $path;
            $user->save();
    
            return response()->json([
                'success' => true,
                'message' => 'Profile picture uploaded successfully!',
                'profile_picture_url' => asset('storage/' . $path)
            ]);
        }
    
        return response()->json([
            'success' => false,
            'message' => 'Failed to upload profile picture.'
        ], 400);
    }

    // Method get profile picture by user_id
    public function getProfilePicture($userId)
    {
        // Find user by user_id
        $user = FokusApp::find($userId);
    
        if (!$user || !$user->profile_picture) {
            return response()->json(['message' => 'Profile picture not found'], 404);
        }
    
        // Return profile picture URL
        return response()->json([
            'success' => true,
            'profile_picture_url' => asset('storage/' . $user->profile_picture)
        ]);
    }
}