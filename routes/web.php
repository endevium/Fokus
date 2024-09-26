<?php

use Illuminate\Support\Facades\Route;
use App\Http\Controllers\notesController;
use App\Models\Note;
//resource routes for the NotesController
Route::resource('notes', notesController::class);

Route::get('/notes', [notesController::class, 'index'])->name('notes.index');
Route::get('/notes/create', [notesController::class, 'create'])->name('notes.create');
Route::post('/notes', [notesController::class, 'store'])->name('notes.store');
Route::get('/notes/{note}', [notesController::class, 'show'])->name('notes.show');
Route::get('/notes/{note}/edit', [notesController::class, 'edit'])->name('notes.edit');
Route::put('/notes/{note}', [notesController::class, 'update'])->name('notes.update');
Route::delete('/notes/{note}', [notesController::class, 'destroy'])->name('notes.destroy');


// Optional: Define a route for the welcome view

Route::get('/notes', [notesController::class, 'index']);


Route::get('/', function () {
    return view('welcome');
});
