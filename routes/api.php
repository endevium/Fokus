<?php

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;
use App\Http\Controllers\NotesController;
use App\Http\Controllers\FokusController;
use Laravel\Sanctum\Http\Controllers\CsrfCookieController;



//TOKEN
Route::get('/sanctum/csrf-cookie', [CsrfCookieController::class, 'showCookie']);


//PROTECTION FROM MIDDLEWARE

Route::middleware('auth:sanctum')->group(function () {
    Route::apiResource('notes', NotesController::class);
});

// Define routes for FokusApp
Route::get('/FokusApp', [fokusController::class, 'index']);
Route::post('/FokusApp', [FokusController::class, 'store']);
Route::get('/FokusApp/{id}', [FokusController::class, 'show']);
Route::post('/login', [FokusController::class, 'login'])->name('login');

// Routes for NotesController
Route::middleware('auth:sanctum')->group(function () {
    Route::apiResource('notes', NotesController::class);
    
    // Additional routes if needed
    Route::post('/notes', [NotesController::class, 'store']);
    Route::put('/notes/{id}', [NotesController::class, 'update']);
    Route::delete('/notes/{id}', [NotesController::class, 'destroy']);
});

// Route to get authenticated user
Route::get('/user', function (Request $request) {
    return $request->user();
})->middleware('auth:sanctum');

// Test route
Route::get('/test', function () {
    return "test";
});
