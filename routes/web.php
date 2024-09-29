<?php

use Illuminate\Support\Facades\Route;
use App\Http\Controllers\NotesController;
use App\Http\Controllers\AuthController;

// Resource routes for the NotesController
Route::resource('notes', NotesController::class);

// For login and signup
Route::post('/login', [AuthController::class, 'login']);
Route::post('/signup', [AuthController::class, 'signup']);

// Optional: Define a route for the welcome view
Route::get('/', function () {
    return view('welcome'); // Ensure you have a view named 'welcome'
});
