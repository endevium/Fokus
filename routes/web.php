<?php

use Illuminate\Support\Facades\Route;
use App\Http\Controllers\NotesController;
use App\Http\Controllers\AuthController;
use App\Http\Controllers\ProfileController;

// Routes for NotesController
Route::resource('notes', NotesController::class);

// For login and signup
Route::post('/login.html', [AuthController::class, 'login']);
Route::post('/signup.html', [AuthController::class, 'signup']); // Corrected spelling

// Route to show the welcome page for the Fokus_app
Route::get('/Fokus_app', function () {
    return view('welcome'); // View named 'welcome'
});

// Route to show the profile upload form
Route::get('/profile', function () {
    return view('profile'); // Returns the profile upload view
});

// Profile picture upload route
Route::post('/profile', [ProfileController::class, 'uploadProfilePicture'])->name('upload.profile.picture');
