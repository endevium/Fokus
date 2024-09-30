<?php

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;
use App\Http\Controllers\NotesController;
use App\Http\Controllers\FokusController;
use Laravel\Sanctum\Http\Controllers\CsrfCookieController;
use App\Http\Controllers\AuthController;



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
    
    // Additional routes
    Route::get('/user_id', function (Request $request) {
        return $request->user(); // Returns authenticated user info
    });

    // BACKUP CHUCHUCHUCHUCHUCHUCHUCHUCHUCH
    
    // Route::post('/fokus_notes', [NotesController::class, 'store']);
    // Route::put('/fokus_notes/{id}', [NotesController::class, 'update']);
    // Route::delete('/fokus_notes/{id}', [NotesController::class, 'destroy']);
});

// FOKUS APP ROUTES
Route::post('/FokusApp', [FokusController::class, 'store']);
Route::get('/FokusApp', [FokusController::class, 'index']);
Route::get('/FokusApp/{id}', [FokusController::class, 'show']);



// Test route
Route::get('/test', function () {
    return "test"; // Simple test route
});
