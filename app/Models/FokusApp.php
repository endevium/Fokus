<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Foundation\Auth\User as Authenticatable;
use Laravel\Sanctum\HasApiTokens;

class FokusApp extends Authenticatable 
{
    use HasFactory, HasApiTokens; 

    protected $table = 'fokus_app';

    protected $fillable = [
        'username', 'password', 'email', 'profile_picture',
    ];

    // Define a relationship to the NotesModel
    public function notes()
    {
        return $this->hasMany(NotesModel::class);

    }

    // Optionally, you can define hidden attributes for the model
    protected $hidden = [
        'password'
    ];
}
