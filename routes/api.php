<?php

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;
use App\Http\Controllers\NotesController;
use App\Http\Controllers\FokusController;
use App\Http\Controllers\AuthController;
use App\Http\Controllers\TaskController;
use Laravel\Sanctum\Http\Controllers\CsrfCookieController;

// Registration route
Route::post('/register', [AuthController::class, 'signup']);

// AUTHENTICATION ROUTES
Route::post('/login', [AuthController::class, 'login']);

// TOKEN ROUTE
Route::get('/sanctum/csrf-cookie', [CsrfCookieController::class, 'showCookie']);

// PROTECTED ROUTES FOR AUTHENTICATED USERS
Route::middleware('auth:sanctum')->group(function () {

    // Notes routes
    Route::apiResource('fokus_notes', NotesController::class);
    
    // Task routes
    Route::apiResource('task', TaskController::class); // RESTful routes for tasks

    // Additional routes
    Route::get('/user_id', function (Request $request) {
        return $request->user(); // Returns authenticated user info
    });
});

// FOKUS APP ROUTES
Route::post('/FokusApp', [FokusController::class, 'store']);
Route::get('/FokusApp', [FokusController::class, 'index']);
Route::get('/FokusApp/{id}', [FokusController::class, 'show']);

// Test route
Route::get('/test', function () {
    return "test"; // Simple test route
});
