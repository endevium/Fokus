<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Storage;
use App\Models\FokusApp;

class ProfileController extends Controller
{
    public function uploadProfilePicture(Request $request)
    {
        $request->validate([
            'profile_picture' => 'required|image|mimes:jpg,jpeg,png|max:5120', // Max size 5MB
        ]);

        // Handle the upload
        if ($request->hasFile('profile_picture')) {
            $file = $request->file('profile_picture');
            $path = $file->store('profiles', 'public');

            $user = auth()->user();
            $user->profile_picture = $path;
            $user->save();

            return redirect()->back()->with('success', 'Profile picture uploaded successfully!');
        }

        return redirect()->back()->with('error', 'Failed to upload profile picture.');
    }
}