<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class PasswordHistory extends Model
{
    use HasFactory;

    protected $fillable = ['user_id', 'password'];

    // Define the relationship with the FokusApp model
    public function user()
    {
        return $this->belongsTo(FokusApp::class);
    }
}
