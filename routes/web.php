<?php

use Illuminate\Support\Facades\Route;
use App\Http\Controllers\NotesController;
use App\Http\Controllers\AuthController;
use App\Http\Controllers\ProfileController;

//routes forNotesController
Route::resource('notes', NotesController::class);

// For login and signup
Route::post('/login.html', [AuthController::class, 'login']);
Route::post('/signup.hrml', [AuthController::class, 'signup']);


// Route to show the welcome page for the Fokus_app
Route::get('/Fokus_app', function () {
    return view('welcome'); // view named 'welcome'
});

Route::post('/upload-profile', [ProfileController::class, 'upload'])->name('profile.upload');
